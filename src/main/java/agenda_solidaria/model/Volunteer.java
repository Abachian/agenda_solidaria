package agenda_solidaria.model;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

    @Column(name = "volunteer_hours")
    private Integer volunteerHours;

    @Column(name = "rating")
    private BigDecimal rating;

    @Column(name = "age")
    private Integer age;

    @Column(name = "location")
    private String location;
    
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