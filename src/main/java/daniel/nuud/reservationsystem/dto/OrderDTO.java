package daniel.nuud.reservationsystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private Long userId;
    private Long houseId;
    private String status;
    private Instant startReservation;
    private Instant endReservation;
}
