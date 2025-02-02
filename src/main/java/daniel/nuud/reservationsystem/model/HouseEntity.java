package daniel.nuud.reservationsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "houses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HouseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private String city;

    @Column(unique = true, nullable = false)
    @NotBlank
    private String address;

    @NotNull
    private Double area;

    @NotNull
    private Integer rooms;

    @NotNull
    private Double price;

    @ElementCollection
    private List<String> imagePaths;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @CreatedDate
    private Instant createdAt = Instant.now();

    @LastModifiedDate
    private Instant updatedAt;
}
