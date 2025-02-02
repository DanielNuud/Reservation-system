package daniel.nuud.reservationsystem.repository;

import daniel.nuud.reservationsystem.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);
    boolean existsById(Long id);
    Optional<UserEntity> findByEmail(String email);
    UserEntity findByEmailIgnoreCase(String email);

    @Query("SELECT u FROM UserEntity u JOIN u.orders o WHERE o.id = :orderId")
    UserEntity findUserByOrderId(@Param("orderId")Long orderId);
}
