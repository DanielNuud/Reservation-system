package daniel.nuud.reservationsystem.order.controller;

import daniel.nuud.reservationsystem.order.dto.OrderCreateDTO;
import daniel.nuud.reservationsystem.order.dto.OrderDTO;
import daniel.nuud.reservationsystem.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(path = "/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder(@RequestBody @Valid OrderCreateDTO dto) {
        return orderService.createOrder(dto);
    }

    @GetMapping(path = "/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping(path = "/orders/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO getOrderById(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
}
