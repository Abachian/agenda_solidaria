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
@Table(name = "organizations")
public class Organization {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_organization")
    private Long id;
    
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "direction", nullable = false)
    private String direction;
    
    @Column(name = "gmap_cordinate")
    private String gmapCoordinate;

    
    @Column(name = "publish_date", nullable = false)
    private LocalDateTime publishDate;
    
    @Column(name = "content", nullable = false)
    private String content;


    @ManyToOne
    @JoinColumn(name = "id_organization_type", nullable = false)
    private OrganizationType organizationType;
    
    @OneToOne(optional = false)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @Column(name = "logo")
    private String logo;

    @ManyToMany
    @JoinTable(
        name = "event_organization",
        joinColumns = @JoinColumn(name = "id_organization"),
        inverseJoinColumns = @JoinColumn(name = "id_event")
    )
    private Set<Event> events;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Image> images;
} 