package daniel.nuud.reservationsystem.util;

import daniel.nuud.reservationsystem.model.UserEntity;
import daniel.nuud.reservationsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {

    @Autowired
    private UserRepository userRepository;

    public UserEntity getUserByEmail() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }
        var email = auth.getName();
        return userRepository.findByEmail(email).get();
    }
}
