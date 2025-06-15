package agenda_solidaria.service;

import java.util.List;
import java.util.Optional;

import agenda_solidaria.model.Need;

public interface NeedService {
    List<Need> getAllNeeds();
    Optional<Need> getNeedById(Long id);
    Need createNeed(Need need);
    Need updateNeed(Long id, Need need);
    void deleteNeed(Long id);
    List<Need> getNeedsByEvent(Long eventId);
}