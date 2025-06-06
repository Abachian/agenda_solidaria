package agenda_solidaria.agenda_solidaria.service;

import agenda_solidaria.agenda_solidaria.model.Rating;
import java.util.List;
import java.util.Optional;

public interface RatingService {
    List<Rating> getAllRatings();
    Optional<Rating> getRatingById(Integer id);
    Rating createRating(Rating rating);
    Rating updateRating(Integer id, Rating rating);
    void deleteRating(Integer id);
} 