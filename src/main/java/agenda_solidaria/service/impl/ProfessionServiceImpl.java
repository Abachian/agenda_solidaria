package agenda_solidaria.service.impl;

import agenda_solidaria.model.Profession;
import agenda_solidaria.repository.ProfessionRepository;
import agenda_solidaria.service.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessionServiceImpl implements ProfessionService {

    @Autowired
    private ProfessionRepository professionRepository;

    @Autowired
    public ProfessionServiceImpl(ProfessionRepository professionRepository) {
        this.professionRepository = professionRepository;
    }

    @Override
    public List<Profession> getAllProfessions() {
        return professionRepository.findAll();
    }

    @Override
    public Optional<Profession> getProfessionById(Long id) {
        return professionRepository.findById(id);
    }

    @Override
    public Profession createProfession(Profession profession) {
        return professionRepository.save(profession);
    }

    @Override
    public Profession updateProfession(Long id, Profession profession) {
        Optional<Profession> professionOptional = professionRepository.findById(id);
        if (professionOptional.isPresent()) {
            Profession updatedProfession = professionOptional.get();
            if (profession.getDescription() != null) updatedProfession.setDescription(profession.getDescription());
            return professionRepository.save(updatedProfession);
        }
        throw new RuntimeException("Profession not found with id: " + id);
    }

    @Override
    public void deleteProfession(Long id) {
        professionRepository.deleteById(id);
    }
} 