package daniel.nuud.reservationsystem.order.model;

import daniel.nuud.reservationsystem.house.model.HouseEntity;
import daniel.nuud.reservationsystem.user.model.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private HouseEntity house;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String status;

    @CreatedDate
    private Instant createdAt;

    private Instant startReservation;
    private Instant endReservation;
}
