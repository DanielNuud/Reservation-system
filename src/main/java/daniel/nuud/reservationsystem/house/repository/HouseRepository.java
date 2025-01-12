package daniel.nuud.reservationsystem.house.repository;

import daniel.nuud.reservationsystem.house.model.HouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<HouseEntity, Long> {
}
