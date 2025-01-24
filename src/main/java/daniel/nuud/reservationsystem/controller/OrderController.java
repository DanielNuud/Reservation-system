package daniel.nuud.reservationsystem.controller;

import daniel.nuud.reservationsystem.dto.OrderDTO;
import daniel.nuud.reservationsystem.model.OrderEntity;
import daniel.nuud.reservationsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String orders(Model model) {
        final List<OrderDTO> list = orderService.getAllOrders();
        model.addAttribute("orders", list);
        return "order/list";
    }


}
