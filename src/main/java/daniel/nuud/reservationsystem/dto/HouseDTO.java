package daniel.nuud.reservationsystem.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;

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
    private Double price;
    private List<MultipartFile> images;
    private List<String> imagePaths;
    private Instant createdAt;
}
