package daniel.nuud.reservationsystem.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class HouseDTO {
    private Long id;
    private String name;
    private String description;
    private String city;
    private String address;
    private Integer rooms;
    private Double area;
    private Instant createdAt;
}
