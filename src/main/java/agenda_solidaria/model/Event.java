package agenda_solidaria.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    @Column(name = "min_age")
    private Integer minAge;
    
    @Column(name= "min_hours")
    private Integer minHours;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "additional_info")
    private String additionalInfo;

    @ManyToOne
    @JoinColumn(name = "id_event_type", nullable = false)
    private EventType eventType;

    @ManyToMany(mappedBy = "events")
    private Set<Organization> organizations;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Set<Need> needs;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Resource> resources;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Image> images;
} 