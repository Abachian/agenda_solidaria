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
@Table(name = "organization")
public class Organization {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idorganization")
    private Integer id;
    
    @Column(name = "description", nullable = false)
    private String description;
    
    @Column(name = "direction", nullable = false)
    private String direction;
    
    @Column(name = "gmap_cordinate")
    private String gmapCoordinate;
    
    @Column(name = "mail", nullable = false)
    private String mail;
    
    @Column(name = "publish_date", nullable = false)
    private LocalDateTime publishDate;
    
    @Column(name = "abstract", nullable = false, columnDefinition = "TEXT")
    private String abstractText;
    
    @Column(name = "telephones")
    private String telephones;
    
    @ManyToOne
    @JoinColumn(name = "idorganizationtype", nullable = false)
    private OrganizationType organizationType;
    
    @ManyToOne
    @JoinColumn(name = "iduser", nullable = false)
    private User user;
    
    @Column(name = "logo")
    private String logo;
    
    @ManyToMany
    @JoinTable(
        name = "event_organization",
        joinColumns = @JoinColumn(name = "idorganization"),
        inverseJoinColumns = @JoinColumn(name = "idevent")
    )
    private Set<Event> events;
} 