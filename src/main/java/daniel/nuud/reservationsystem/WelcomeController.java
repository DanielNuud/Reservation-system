package daniel.nuud.reservationsystem;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/welcome")
public class WelcomeController {

    @GetMapping(path = "")
    public String welcome() {
        return "Welcome to the Reservation System";
    }
}
