package daniel.nuud.reservationsystem.repository;

import daniel.nuud.reservationsystem.dto.HouseDTO;
import daniel.nuud.reservationsystem.dto.OrderDTO;
import daniel.nuud.reservationsystem.model.HouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<HouseEntity, Long> {
    boolean existsById(Long id);
    boolean existsByAddress(String address);
    List<HouseDTO> findByCity(String city);
}
