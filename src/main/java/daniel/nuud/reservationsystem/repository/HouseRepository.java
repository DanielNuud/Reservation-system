package daniel.nuud.reservationsystem.repository;

import daniel.nuud.reservationsystem.dto.HouseDTO;
import daniel.nuud.reservationsystem.dto.OrderDTO;
import daniel.nuud.reservationsystem.model.HouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<HouseEntity, Long> {
    boolean existsById(Long id);
    boolean existsByAddress(String address);
    List<HouseDTO> findByCity(String city);

    @Query("SELECT h FROM HouseEntity h WHERE h.city = :city " +
            "AND h.id NOT IN (SELECT o.house.id FROM OrderEntity o WHERE " +
            "(o.startReservation <= :endReservation AND o.endReservation >= :startReservation))")
    List<HouseEntity> findAvailableHouses(@Param("city") String city,
                                          @Param("startReservation") Instant startReservation,
                                          @Param("endReservation") Instant endReservation);

    @Query("select h FROM HouseEntity h where h.user.id = :userId")
    List<HouseEntity> findByUserId(@Param("userId") Long userId);

    List<HouseEntity> findByCreatedAtAfter(Instant createdAt);
}
