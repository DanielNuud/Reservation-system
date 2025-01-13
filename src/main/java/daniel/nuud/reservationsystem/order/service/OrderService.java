package daniel.nuud.reservationsystem.order.service;

import daniel.nuud.reservationsystem.house.repository.HouseRepository;
import daniel.nuud.reservationsystem.order.dto.OrderCreateDTO;
import daniel.nuud.reservationsystem.order.dto.OrderDTO;
import daniel.nuud.reservationsystem.order.mapper.OrderMapper;
import daniel.nuud.reservationsystem.order.model.OrderEntity;
import daniel.nuud.reservationsystem.order.repository.OrderRepository;
import daniel.nuud.reservationsystem.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private OrderMapper orderMapper;

    public OrderDTO createOrder(OrderCreateDTO orderCreateDTO) {
        var user = userRepository.findById(orderCreateDTO.getUserId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var house = houseRepository.findById(orderCreateDTO.getHouseId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        OrderEntity order = orderMapper.toEntity(orderCreateDTO);
        order.setUser(user);
        order.setHouse(house);
        order.setStatus("CONFIRMED");
        return orderMapper.toDto(orderRepository.save(order));
    }

    public OrderDTO getOrder(Long orderId) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return orderMapper.toDto(order);
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteOrder(Long orderId) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        orderRepository.delete(order);
    }
}
