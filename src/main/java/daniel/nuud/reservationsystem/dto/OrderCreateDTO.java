package daniel.nuud.reservationsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class OrderCreateDTO {
    @NotNull(message = "User ID must not be null")
    private Long userId;

    @NotNull(message = "House ID must not be null")
    private Long houseId;

    @NotNull(message = "Start date must not be null")
    private Instant startReservation;

    @NotNull(message = "End date must not be null")
    private Instant endReservation;
}
