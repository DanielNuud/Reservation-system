package daniel.nuud.reservationsystem.controller;

import daniel.nuud.reservationsystem.dto.HouseCreateDTO;
import daniel.nuud.reservationsystem.dto.HouseDTO;
import daniel.nuud.reservationsystem.dto.HouseUpdateDTO;
import daniel.nuud.reservationsystem.service.HouseService;
import daniel.nuud.reservationsystem.util.ReferencedWarning;
import daniel.nuud.reservationsystem.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(path = "/houses")
public class HouseController {

    @Autowired
    private HouseService houseService;

    @GetMapping
    public String list(final Model model) {
        final List<HouseDTO> houses = houseService.getAllHouses();
        model.addAttribute("houses", houses);
        return "house/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("house") final HouseCreateDTO houseCreateDTO) {
        return "house/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("house") @Valid final HouseCreateDTO houseCreateDTO,
                      final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "house/add";
        }
        houseService.createHouse(houseCreateDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("house.create.success"));
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
                       final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "house/edit";
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
