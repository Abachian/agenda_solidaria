package agenda_solidaria.model;

import java.time.LocalDateTime;
import java.util.Set;

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
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_event")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "intro")
    private String intro;

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

    @ManyToOne
    @JoinColumn(name = "id_event_type", nullable = false)
    private EventType eventType;

    @ManyToMany(mappedBy = "events")
    private Set<Organization> organizations;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Set<Need> needs;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Image> images;
} 