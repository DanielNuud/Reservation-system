package daniel.nuud.reservationsystem.controller;

import daniel.nuud.reservationsystem.dto.HouseCreateDTO;
import daniel.nuud.reservationsystem.dto.HouseDTO;
import daniel.nuud.reservationsystem.dto.HouseUpdateDTO;
import daniel.nuud.reservationsystem.dto.UserDTO;
import daniel.nuud.reservationsystem.exception.AccessDeniedException;
import daniel.nuud.reservationsystem.exception.ResourceNotFoundException;
import daniel.nuud.reservationsystem.model.HouseEntity;
import daniel.nuud.reservationsystem.model.UserEntity;
import daniel.nuud.reservationsystem.repository.HouseRepository;
import daniel.nuud.reservationsystem.service.HouseService;
import daniel.nuud.reservationsystem.service.UserService;
import daniel.nuud.reservationsystem.util.ReferencedWarning;
import daniel.nuud.reservationsystem.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(path = "/houses")
public class HouseController {

    @Autowired
    private HouseService houseService;

    @Autowired
    private UserService userService;

    @Autowired
    private HouseRepository houseRepository;

//    @GetMapping
//    public String list(final Model model) {
//        final List<HouseDTO> houses = houseService.getAllHouses();
//        model.addAttribute("houses", houses);
//        return "house/list";
//    }

    @GetMapping
    public String myHouses(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        var currentUser = userService.findByUserName(userDetails.getUsername());
        model.addAttribute("houses", houseService.getHousesByUser(currentUser));
        return "house/list";
    }

    @GetMapping("/{id}")
    public String viewHouse(@PathVariable Long id, Model model) {
        var house = houseService.findHouseById(id);
        model.addAttribute("house", house);
        return "house/view";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("house") final HouseCreateDTO houseCreateDTO) {
        return "house/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("house") @Valid final HouseCreateDTO houseCreateDTO,
                      final BindingResult bindingResult,
                      final RedirectAttributes redirectAttributes,
                      @AuthenticationPrincipal UserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            return "house/add";
        }

        try {
            List<String> imagePaths = new ArrayList<>();
            if (houseCreateDTO.getImages() != null && !houseCreateDTO.getImages().isEmpty()) {
                Path uploadDir = Paths.get("src/main/resources/static/uploads");
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }

                for (MultipartFile image : houseCreateDTO.getImages()) {
                    if (!image.isEmpty()) {
                        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
                        Path filePath = uploadDir.resolve(fileName);
                        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                        imagePaths.add("/uploads/" + fileName);
                    }
                }
            }

            houseCreateDTO.setImagePaths(imagePaths);
            var user = userService.findByUserName(userDetails.getUsername());
            houseService.createHouse(houseCreateDTO, user);

            redirectAttributes.addFlashAttribute("success", "House successfully added!");
        } catch (IOException e) {
            bindingResult.rejectValue("images", "error.house", "Error uploading images");
            return "house/add";
        }

        return "redirect:/houses";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("house", houseService.findHouseById(id));
        return "house/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
                       @ModelAttribute("house") @Valid final HouseUpdateDTO houseUpdateDTO,
                       final BindingResult bindingResult, final RedirectAttributes redirectAttributes,
                       @AuthenticationPrincipal UserDetails userDetails) {

        HouseEntity house = houseRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("House with id " + id + " not found"));

        if (bindingResult.hasErrors()) {
            return "house/edit";
        }

        if (!house.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new AccessDeniedException("You do not have permission to edit this house");
        }

        houseService.updateHouse(id, houseUpdateDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("house.update.success"));
        return "redirect:/houses";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
                         final RedirectAttributes redirectAttributes) {
        final ReferencedWarning referencedWarning = houseService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR,
                    WebUtils.getMessage(referencedWarning.getKey(), referencedWarning.getParams().toArray()));
        } else {
            houseService.deleteHouse(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("house.delete.success"));
        }
        return "redirect:/houses";
    }

}
