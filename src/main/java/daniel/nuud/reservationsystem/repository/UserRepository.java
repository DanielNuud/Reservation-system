package daniel.nuud.reservationsystem.repository;

import daniel.nuud.reservationsystem.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);
    boolean existsById(Long id);
    Optional<UserEntity> findByEmail(String email);
}
