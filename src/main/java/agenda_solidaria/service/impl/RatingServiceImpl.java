package agenda_solidaria.service.impl;

import agenda_solidaria.model.Rating;
import agenda_solidaria.repository.RatingRepository;
import agenda_solidaria.service.RatingService;
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
    public Optional<Rating> getRatingById(Long id) {
        return ratingRepository.findById(id);
    }

    @Override
    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public Rating updateRating(Long id, Rating rating) {
        Optional<Rating> optionalRating = ratingRepository.findById(id);
        if (optionalRating.isPresent()) {
            Rating updatedRating = optionalRating.get();
            if(rating.getRating() != null)  updatedRating.setRating(rating.getRating());
            if(rating.getDescription() != null)  updatedRating.setDescription(rating.getDescription());
            if (rating.getPublishDate() != null)  updatedRating.setPublishDate(rating.getPublishDate());
            return ratingRepository.save(rating);
        }
        throw new RuntimeException("Rating not found with id: " + id);
    }

    @Override
    public void deleteRating(Long id) {
        ratingRepository.deleteById(id);
    }

    @Override
    public List<Rating> getRatingsByVolunteer(Long volunteerId) {
        return ratingRepository.findByVolunteerId(volunteerId);
    }

    @Override
    public List<Rating> getRatingsByOrganization(Long organizationId) {
        return ratingRepository.findByOrganizationId(organizationId);
    }
} 