package agenda_solidaria.agenda_solidaria.service.impl;

import agenda_solidaria.agenda_solidaria.model.Need;
import agenda_solidaria.agenda_solidaria.repository.NeedRepository;
import agenda_solidaria.agenda_solidaria.service.NeedService;
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
    public Optional<Need> getNeedById(Integer id) {
        return needRepository.findById(id);
    }

    @Override
    public Need createNeed(Need need) {
        return needRepository.save(need);
    }

    @Override
    public Need updateNeed(Integer id, Need need) {
        if (needRepository.existsById(id)) {
            need.setId(id);
            return needRepository.save(need);
        }
        throw new RuntimeException("Need not found with id: " + id);
    }

    @Override
    public void deleteNeed(Integer id) {
        needRepository.deleteById(id);
    }

    @Override
    public List<Need> getNeedsByEvent(Integer eventId) {
        return needRepository.findByEventId(eventId);
    }

    @Override
    public List<Need> getNeedsByProfession(Integer professionId) {
        return needRepository.findByProfessionId(professionId);
    }
} 