package agenda_solidaria.agenda_solidaria.service;

import agenda_solidaria.agenda_solidaria.model.Need;
import java.util.List;
import java.util.Optional;

public interface NeedService {
    List<Need> getAllNeeds();
    Optional<Need> getNeedById(Integer id);
    Need createNeed(Need need);
    Need updateNeed(Integer id, Need need);
    void deleteNeed(Integer id);
} 