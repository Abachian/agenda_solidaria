package agenda_solidaria.model;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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

    @Column(name = "mission")
    private String mission;

    @Column(name = "vision")
    private String vision;

    @Column(name = "website")
    private String website;

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

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