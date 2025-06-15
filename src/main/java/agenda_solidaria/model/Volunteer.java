package agenda_solidaria.model;

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
@Table(name = "volunteers")
public class Volunteer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_volunteer")
    private Long id;

    @Column(name = "document", nullable = false)
    private String document;

    @Column(name = "document_type")
    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
    
    @OneToOne(optional = false)
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
        name = "volunteer_profession",
        joinColumns = @JoinColumn(name = "id_volunteer"),
        inverseJoinColumns = @JoinColumn(name = "id_profession")
    )
    private Set<Profession> professions;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Image> images;

    @OneToMany(mappedBy = "volunteer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Postulate> postulates;
} 