package agenda_solidaria.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrating")
    private Long id;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "publish_date", nullable = false)
    private LocalDateTime publishDate;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "idvolunteer", nullable = false)
    private Volunteer volunteer;

    @ManyToOne
    @JoinColumn(name = "idorganization", nullable = false)
    private Organization organization;
} 