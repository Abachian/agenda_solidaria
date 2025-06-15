package agenda_solidaria.service.impl;

import agenda_solidaria.model.Need;
import agenda_solidaria.repository.NeedRepository;
import agenda_solidaria.service.NeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NeedServiceImpl implements NeedService {

    @Autowired
    private NeedRepository needRepository;

    @Override
    public List<Need> getAllNeeds() {
        return needRepository.findAll();
    }

    @Override
    public Optional<Need> getNeedById(Long id) {
        return needRepository.findById(id);
    }

    @Override
    public Need createNeed(Need need) {
        return needRepository.save(need);
    }

    @Override
    public Need updateNeed(Long id, Need need) {
        Optional<Need> needOptional = needRepository.findById(id);
        if (needOptional.isPresent()) {
            Need updatedNeed = needOptional.get();
            if (need.getDescription() != null) updatedNeed.setDescription(need.getDescription());
            if (need.getPublishDate() != null) updatedNeed.setPublishDate(need.getPublishDate());
            return needRepository.save(updatedNeed);
        }
        throw new RuntimeException("Need not found with id: " + id);
    }

    @Override
    public void deleteNeed(Long id) {
        needRepository.deleteById(id);
    }

    @Override
    public List<Need> getNeedsByEvent(Long eventId) {
        return needRepository.findByEventId(eventId);
    }


} 