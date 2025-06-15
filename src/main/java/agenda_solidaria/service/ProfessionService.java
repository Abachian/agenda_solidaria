package agenda_solidaria.service;

import agenda_solidaria.model.Profession;
import java.util.List;
import java.util.Optional;

public interface ProfessionService {
    List<Profession> getAllProfessions();
    Optional<Profession> getProfessionById(Long id);
    Profession createProfession(Profession profession);
    Profession updateProfession(Long id, Profession profession);
    void deleteProfession(Long id);
} 