package daniel.nuud.reservationsystem.repository;

import daniel.nuud.reservationsystem.model.HouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<HouseEntity, Long> {
    boolean existsById(Long id);
    boolean existsByAddress(String address);
}
