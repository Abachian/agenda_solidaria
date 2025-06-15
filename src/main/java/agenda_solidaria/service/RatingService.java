package agenda_solidaria.service;

import java.util.List;
import java.util.Optional;

import agenda_solidaria.model.Rating;

public interface RatingService {
    List<Rating> getAllRatings();
    Optional<Rating> getRatingById(Long id);
    Rating createRating(Rating rating);
    Rating updateRating(Long id, Rating rating);
    void deleteRating(Long id);
    List<Rating> getRatingsByVolunteer(Long volunteerId);
    List<Rating> getRatingsByOrganization(Long organizationId);
} 