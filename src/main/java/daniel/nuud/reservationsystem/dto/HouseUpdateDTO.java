package daniel.nuud.reservationsystem.dto;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class HouseUpdateDTO {
    private String name;
    private String description;
    private String city;
    private String address;
    private Double area;
    private Integer rooms;
    private Double price;
    private MultipartFile image;
}
