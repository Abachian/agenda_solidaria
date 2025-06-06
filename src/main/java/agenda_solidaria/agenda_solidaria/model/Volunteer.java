package agenda_solidaria.agenda_solidaria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "volunteer")
public class Volunteer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idvolunteer")
    private Integer id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "lastname", nullable = false)
    private String lastname;
    
    @Column(name = "document", nullable = false)
    private String document;
    
    @Column(name = "mail", nullable = false)
    private String mail;
    
    @ManyToOne
    @JoinColumn(name = "iddocumenttype", nullable = false)
    private DocumentType documentType;
    
    @ManyToOne
    @JoinColumn(name = "iduser", nullable = false)
    private User user;
    
    @Column(name = "telephones")
    private String telephones;
    
    @ManyToMany
    @JoinTable(
        name = "volunteer_profession",
        joinColumns = @JoinColumn(name = "idvolunteer"),
        inverseJoinColumns = @JoinColumn(name = "idprofession")
    )
    private Set<Profession> professions;
} 