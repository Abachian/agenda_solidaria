package agenda_solidaria.agenda_solidaria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idevent")
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "direction", nullable = false)
    private String direction;

    @Column(name = "gmap_cordinate")
    private String gmapCoordinate;

    @Column(name = "publish_date", nullable = false)
    private LocalDateTime publishDate;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "abstract", nullable = false, columnDefinition = "TEXT")
    private String abstractText;

    @ManyToOne
    @JoinColumn(name = "ideventtype", nullable = false)
    private EventType eventType;

    @ManyToOne
    @JoinColumn(name = "idorganization", nullable = false)
    private Organization organization;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Set<Image> images;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Set<Need> needs;
} 