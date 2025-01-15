package daniel.nuud.reservationsystem.user.repository;

import daniel.nuud.reservationsystem.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);
    boolean existsById(Long id);
    boolean existsByPhoneNumber(String phone);
}
