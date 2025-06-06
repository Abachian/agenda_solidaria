package agenda_solidaria.agenda_solidaria.service.impl;

import agenda_solidaria.agenda_solidaria.model.Rating;
import agenda_solidaria.agenda_solidaria.repository.RatingRepository;
import agenda_solidaria.agenda_solidaria.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;
            
    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public Optional<Rating> getRatingById(Integer id) {
        return ratingRepository.findById(id);
    }

    @Override
    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public Rating updateRating(Integer id, Rating rating) {
        if (ratingRepository.existsById(id)) {
            rating.setId(id);
            return ratingRepository.save(rating);
        }
        throw new RuntimeException("Rating not found with id: " + id);
    }

    @Override
    public void deleteRating(Integer id) {
        ratingRepository.deleteById(id);
    }

    @Override
    public List<Rating> getRatingsByVolunteer(Integer volunteerId) {
        return ratingRepository.findByVolunteerId(volunteerId);
    }

    @Override
    public List<Rating> getRatingsByOrganization(Integer organizationId) {
        return ratingRepository.findByOrganizationId(organizationId);
    }
} 