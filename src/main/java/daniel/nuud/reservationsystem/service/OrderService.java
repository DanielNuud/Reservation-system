package daniel.nuud.reservationsystem.service;

import daniel.nuud.reservationsystem.exception.ConflictException;
import daniel.nuud.reservationsystem.exception.IllegalTimeException;
import daniel.nuud.reservationsystem.exception.ResourceNotFoundException;
import daniel.nuud.reservationsystem.repository.HouseRepository;
import daniel.nuud.reservationsystem.dto.OrderCreateDTO;
import daniel.nuud.reservationsystem.dto.OrderDTO;
import daniel.nuud.reservationsystem.mapper.OrderMapper;
import daniel.nuud.reservationsystem.model.OrderEntity;
import daniel.nuud.reservationsystem.repository.OrderRepository;
import daniel.nuud.reservationsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
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

    @Autowired
    private EmailService emailService;

    @Scheduled(fixedDelay = 60000)
    public void cancelUnpaidReservations() {
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);
        List<OrderEntity> unpaidOrders = orderRepository.findUnpaidOrders(tenMinutesAgo);

        for (OrderEntity order : unpaidOrders) {
            orderRepository.delete(order);
            emailService.sendEmail(order.getUser().getEmail(),
                    "Reservation Cancelled",
                    "Your reservation for house " + order.getHouse().getName() + " was cancelled due to non-payment.");
            System.out.println("Order " + order.getId() + " was canceled due to no payment.");
        }
    }

    public OrderDTO createOrder(OrderCreateDTO orderCreateDTO) {
        var user = userRepository.findById(orderCreateDTO.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User with id " + orderCreateDTO.getUserId() + " not found"));

        var house = houseRepository.findById(orderCreateDTO.getHouseId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("House with id " + orderCreateDTO.getHouseId() + " not found"));

        if (orderCreateDTO.getStartReservation().isAfter(orderCreateDTO.getEndReservation())) {
            throw new IllegalTimeException("Start reservation cannot be after end reservation");
        }

        boolean hasConflict = orderRepository.existsByHouseAndTimeRange(
                house.getId(),
                orderCreateDTO.getStartReservation(),
                orderCreateDTO.getEndReservation()
        );

        if (hasConflict) {
            throw new ConflictException("House is already booked for the specified time range");
        }

        OrderEntity order = orderMapper.toEntity(orderCreateDTO);
        order.setUser(user);
        order.setHouse(house);
        order.setStatus("CONFIRMED");
        orderRepository.save(order);


        emailService.sendEmail(user.getEmail(),
                "Reservation confirmed",
                "Your order has been confirmed. Waiting for payment in 10 minutes.");

        return orderMapper.toDto(order);
    }

    public OrderDTO getOrder(Long orderId) {
        var order = orderRepository.findById(orderId)
                .orElseThrow(()-> new ResourceNotFoundException("Order with id " + orderId + " not found"));
        return orderMapper.toDto(order);
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new ResourceNotFoundException("Order with id " + orderId + " not found");
        }

        orderRepository.deleteById(orderId);
    }
}
