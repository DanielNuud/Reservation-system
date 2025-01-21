package daniel.nuud.reservationsystem.component;

import daniel.nuud.reservationsystem.model.UserEntity;
import daniel.nuud.reservationsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("local")
public class UserLoader implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        if (userRepository.count() != 0) {
            return;
        }

        final UserEntity user = new UserEntity();
        user.setEmail("daniel@gmail.com");
        user.setPassword(passwordEncoder.encode("123456"));
        userRepository.save(user);
    }
}
