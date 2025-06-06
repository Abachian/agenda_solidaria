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
@Table(name = "organization_type")
public class OrganizationType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idorganizationtype")
    private Integer idOrganizationType;
    
    @Column(name = "description", nullable = false, length = 45)
    private String description;
} 