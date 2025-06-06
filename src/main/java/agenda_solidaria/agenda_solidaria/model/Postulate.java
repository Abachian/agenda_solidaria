package agenda_solidaria.agenda_solidaria.model;

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
@Table(name = "postulate")
public class Postulate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpostulate")
    private Integer id;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "publish_date", nullable = false)
    private LocalDateTime publishDate;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    @ManyToOne
    @JoinColumn(name = "idvolunteer", nullable = false)
    private Volunteer volunteer;

    @ManyToOne
    @JoinColumn(name = "idneed", nullable = false)
    private Need need;
} 