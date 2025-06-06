package agenda_solidaria.agenda_solidaria.service.impl;

import agenda_solidaria.agenda_solidaria.model.Profession;
import agenda_solidaria.agenda_solidaria.repository.ProfessionRepository;
import agenda_solidaria.agenda_solidaria.service.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessionServiceImpl implements ProfessionService {

    private final ProfessionRepository professionRepository;

    @Autowired
    public ProfessionServiceImpl(ProfessionRepository professionRepository) {
        this.professionRepository = professionRepository;
    }

    @Override
    public List<Profession> getAllProfessions() {
        return professionRepository.findAll();
    }

    @Override
    public Optional<Profession> getProfessionById(Integer id) {
        return professionRepository.findById(id);
    }

    @Override
    public Profession createProfession(Profession profession) {
        return professionRepository.save(profession);
    }

    @Override
    public Profession updateProfession(Integer id, Profession profession) {
        if (professionRepository.existsById(id)) {
            profession.setIdProfession(id);
            return professionRepository.save(profession);
        }
        throw new RuntimeException("Profession not found with id: " + id);
    }

    @Override
    public void deleteProfession(Integer id) {
        professionRepository.deleteById(id);
    }
} 