package daniel.nuud.reservationsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @Positive(message = "Area must be greater than 0")
    private Double area;

    @Positive(message = "Number of rooms must be greater than 0")
    private Integer rooms;

    private boolean available;
}
