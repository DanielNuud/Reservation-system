package daniel.nuud.reservationsystem.house.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class HouseDTO {
    private Long id;
    private String name;
    private String address;
    private Integer capacity;
    private Boolean available;
    private Instant createdAt;
}
