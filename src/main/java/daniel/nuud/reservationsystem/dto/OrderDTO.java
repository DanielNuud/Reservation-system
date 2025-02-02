package daniel.nuud.reservationsystem.dto;

import daniel.nuud.reservationsystem.model.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private UserDTO user;
    private HouseDTO house;
    private OrderStatus status;
    private Instant startReservation;
    private Instant endReservation;
}
