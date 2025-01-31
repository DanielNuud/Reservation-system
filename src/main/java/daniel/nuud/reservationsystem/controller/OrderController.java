package daniel.nuud.reservationsystem.controller;

import daniel.nuud.reservationsystem.dto.OrderCreateDTO;
import daniel.nuud.reservationsystem.dto.OrderDTO;
import daniel.nuud.reservationsystem.mapper.OrderMapper;
import daniel.nuud.reservationsystem.model.OrderEntity;
import daniel.nuud.reservationsystem.service.HouseService;
import daniel.nuud.reservationsystem.service.OrderService;
import daniel.nuud.reservationsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
        final List<OrderDTO> list = orderService.getAllOrders();
        model.addAttribute("orders", list);
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

//    @PostMapping("/{id}")
//    public String createOrder(@PathVariable Long id, Model model,
//                              @AuthenticationPrincipal UserDetails userDetails) {
//
//    }

}
