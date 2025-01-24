package daniel.nuud.reservationsystem.repository;

import daniel.nuud.reservationsystem.model.HouseEntity;
import daniel.nuud.reservationsystem.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT COUNT(o) > 0 FROM OrderEntity o WHERE o.house.id = :houseId AND " +
            "((o.startReservation <= :endReservation AND o.endReservation >= :startReservation))")
    boolean existsByHouseAndTimeRange(@Param("houseId") Long houseId,
                                      @Param("startReservation") Instant startReservation,
                                      @Param("endReservation") Instant endReservation);

    OrderEntity findFirstByHouse(HouseEntity houseEntity);

}
