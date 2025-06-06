package agenda_solidaria.agenda_solidaria.service;

import agenda_solidaria.agenda_solidaria.model.Profession;
import java.util.List;
import java.util.Optional;

public interface ProfessionService {
    List<Profession> getAllProfessions();
    Optional<Profession> getProfessionById(Integer id);
    Profession createProfession(Profession profession);
    Profession updateProfession(Integer id, Profession profession);
    void deleteProfession(Integer id);
} 