package agenda_solidaria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import agenda_solidaria.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByVolunteerId(Long volunteerId);
    List<Rating> findByOrganizationId(Long organizationId);
} 