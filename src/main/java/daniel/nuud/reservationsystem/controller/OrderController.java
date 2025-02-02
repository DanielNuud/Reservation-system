package daniel.nuud.reservationsystem.controller;

import daniel.nuud.reservationsystem.dto.OrderCreateDTO;
import daniel.nuud.reservationsystem.dto.OrderDTO;
import daniel.nuud.reservationsystem.mapper.OrderMapper;
import daniel.nuud.reservationsystem.model.OrderEntity;
import daniel.nuud.reservationsystem.service.HouseService;
import daniel.nuud.reservationsystem.service.OrderService;
import daniel.nuud.reservationsystem.service.UserService;
import daniel.nuud.reservationsystem.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private HouseService houseService;

    @GetMapping("/orders")
    public String orders(Model model) {
        model.addAttribute("orders", orderService.getListGroupByCities());
        return "order/list";
    }

    @GetMapping("/houses/{id}/book")
    public String showOrderForm(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        var house = houseService.findHouseById(id);
        var user = userService.findByUserName(userDetails.getUsername());

        model.addAttribute("house", house);
        model.addAttribute("user", user);
        model.addAttribute("order", new OrderCreateDTO());

        return "order/view";
    }

    @PostMapping("/houses/{houseId}/book")
    public String createOrder(@PathVariable Long houseId,
                              @ModelAttribute OrderCreateDTO orderCreateDTO,
                              @RequestParam("checkIn") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate startReservation,
                              @RequestParam("checkOut") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate endReservation,
                              @AuthenticationPrincipal UserDetails userDetails,
                              RedirectAttributes redirectAttributes) {

        var user = userService.findByUserName(userDetails.getUsername());
        var house = houseService.findHouseById(houseId);

        Instant start = startReservation.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant end = endReservation.atStartOfDay(ZoneId.systemDefault()).toInstant();

        orderCreateDTO.setStartReservation(start);
        orderCreateDTO.setEndReservation(end);
        orderCreateDTO.setHouseId(house.getId());
        orderCreateDTO.setUserId(user.getId());

        orderService.createOrder(orderCreateDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS,  WebUtils.getMessage("order.create.success"));

        return "redirect:/orders";
    }

    @PostMapping("/orders/cancel/{id}")
    public String cancelOrder(@PathVariable(name = "id") Long id, RedirectAttributes redirectAttributes) {

        try {
            orderService.cancelOrder(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS,  WebUtils.getMessage("order.cancel.success"));
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, "Order is already canceled.");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, WebUtils.getMessage(e.getMessage()));
        }

        return "redirect:/orders";
    }

    @PostMapping("/orders/{id}")
    public String deleteOrder(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        orderService.deleteOrder(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, "Order successfully deleted!");
        return "redirect:/orders";
    }

}
