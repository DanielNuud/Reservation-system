package daniel.nuud.reservationsystem.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RegistrationRequest {

    @Size(max = 255)
    private String firstName;

    @Size(max = 255)
    private String lastName;

    @NotNull
    @Size(max = 255)
//    @UserEmailUnique(message = "{registration.register.taken}")
    private String email;

    @NotNull
    @Size(max = 72)
    private String password;

    private List<@Size(max = 255) String> roles;

}
