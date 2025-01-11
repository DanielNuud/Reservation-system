package daniel.nuud.reservationsystem.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class UserUpdateDTO {
    private String email;
    private String phone;
    private Instant updatedAt = Instant.now();
}
