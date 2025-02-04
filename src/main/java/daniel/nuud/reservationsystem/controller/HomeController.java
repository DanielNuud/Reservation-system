package daniel.nuud.reservationsystem.controller;

import daniel.nuud.reservationsystem.dto.HouseDTO;
import daniel.nuud.reservationsystem.service.HouseService;
import daniel.nuud.reservationsystem.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private HouseService houseService;

    @GetMapping("/")
    public String index(
            @RequestParam(name = "logoutSuccess", required = false) final Boolean logoutSuccess,
            final Model model) {
        if (logoutSuccess == Boolean.TRUE) {
            model.addAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("authentication.logout.success"));
        }
        var houses = houseService.getAllHouses();
        model.addAttribute("houses", houses);
        model.addAttribute("houseCount", houses.size());
        return "home/index";
    }


    @GetMapping("/available")
    public String findAvailableHouses(@RequestParam("city") String city,
                                      @RequestParam("checkin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate startReservation,
                                      @RequestParam("checkout") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate endReservation,
                                      Model model) {

        Instant start = startReservation.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant end = endReservation.atStartOfDay(ZoneId.systemDefault()).toInstant();

        List<HouseDTO> availableHouses = houseService.findAvailableHouses(city, start, end);
        System.out.println("Available houses: " + availableHouses);
        model.addAttribute("houses", availableHouses);
        model.addAttribute("city", city);
        model.addAttribute("houseCount", availableHouses.size());
        return "home/index";
    }

}