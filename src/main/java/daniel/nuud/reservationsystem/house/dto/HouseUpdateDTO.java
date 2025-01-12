package daniel.nuud.reservationsystem.house.dto;

import lombok.Getter;
import lombok.Setter;
import org.openapitools.jackson.nullable.JsonNullable;

@Getter
@Setter
public class HouseUpdateDTO {
    private JsonNullable<String> name;
    private JsonNullable<String> description;
    private JsonNullable<String> address;
    private JsonNullable<Integer> capacity;
    private JsonNullable<Boolean> available;
}
