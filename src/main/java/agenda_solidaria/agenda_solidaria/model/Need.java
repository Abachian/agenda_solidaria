package agenda_solidaria.agenda_solidaria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "need")
public class Need {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idneed")
    private Integer id;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "idevent", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "idprofession", nullable = false)
    private Profession profession;
} 