package daniel.nuud.reservationsystem.component;

import daniel.nuud.reservationsystem.model.UserEntity;
import daniel.nuud.reservationsystem.repository.UserRepository;
import daniel.nuud.reservationsystem.service.CustomUserDetailsService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class DataInitializer implements ApplicationRunner {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final CustomUserDetailsService userService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.findByEmail("daniel@gmail.com").isEmpty()) {
            var email = "daniel@gmail.com";
            var userData = new UserEntity();
            userData.setEmail(email);
            userData.setPassword("123456");
            userService.createUser(userData);
        }
    }

}
