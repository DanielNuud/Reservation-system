package daniel.nuud.reservationsystem.dto;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class HouseUpdateDTO {
    private JsonNullable<String> name;
    private JsonNullable<String> description;
    private JsonNullable<String> city;
    private JsonNullable<String> address;
    private JsonNullable<Double> area;
    private JsonNullable<Integer> rooms;
    private JsonNullable<MultipartFile> image;
}
