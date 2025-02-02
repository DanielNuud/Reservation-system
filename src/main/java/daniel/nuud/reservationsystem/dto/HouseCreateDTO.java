package daniel.nuud.reservationsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class HouseCreateDTO {
    @NotBlank(message = "Name must not be blank")
    private String name;

    private String description;

    @NotBlank(message = "City must be not blank")
    private String city;

    @NotBlank(message = "Address must not be blank")
    private String address;

    @Positive(message = "Must be greater than 0")
    private Double area;

    @Positive(message = "Must be greater than 0")
    private Integer rooms;

    @Positive(message = "Must be greater than 0")
    private Double price;

    private List<MultipartFile> images;

    private List<String> imagePaths;
}
