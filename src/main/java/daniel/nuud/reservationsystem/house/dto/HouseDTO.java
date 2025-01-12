package daniel.nuud.reservationsystem.house.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseDTO {
    private Long id;
    private String name;
    private String address;
    private Integer capacity;
    private Boolean available;
}
