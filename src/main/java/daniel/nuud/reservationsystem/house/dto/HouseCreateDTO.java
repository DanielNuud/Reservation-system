package daniel.nuud.reservationsystem.house.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseCreateDTO {
    @NotBlank(message = "Name must not be blank")
    private String name;

    private String description;

    @NotBlank(message = "Address must not be blank")
    private String address;

    @NotNull(message = "Capacity must not be null")
    private Integer capacity;
}
