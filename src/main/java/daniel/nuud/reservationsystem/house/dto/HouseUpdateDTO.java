package daniel.nuud.reservationsystem.house.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseUpdateDTO {
    private String name;
    private String description;
    private String location;
    private Integer capacity;
    private Boolean available;
}
