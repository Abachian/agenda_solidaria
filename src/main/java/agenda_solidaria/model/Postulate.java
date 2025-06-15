package agenda_solidaria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "postulates")
public class Postulate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_postulate")
    private Integer id;

    @Column(name = "postulated_date", nullable = false)
    private LocalDateTime postulated_date;

    @Column(name = "acepted", nullable = false)
    private Boolean acepted = true;

    @ManyToOne
    @JoinColumn(name = "id_volunteer", nullable = false)
    private Volunteer volunteer;

    @ManyToOne
    @JoinColumn(name = "id_need", nullable = false)
    private Need need;
} 