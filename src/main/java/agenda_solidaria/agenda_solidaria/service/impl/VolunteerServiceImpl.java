package agenda_solidaria.agenda_solidaria.service.impl;

import agenda_solidaria.agenda_solidaria.model.Volunteer;
import agenda_solidaria.agenda_solidaria.repository.VolunteerRepository;
import agenda_solidaria.agenda_solidaria.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VolunteerServiceImpl implements VolunteerService {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Override
    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    @Override
    public Optional<Volunteer> getVolunteerById(Integer id) {
        return volunteerRepository.findById(id);
    }

    @Override
    public Volunteer createVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    @Override
    public Volunteer updateVolunteer(Integer id, Volunteer volunteer) {
        if (volunteerRepository.existsById(id)) {
            volunteer.setId(id);
            return volunteerRepository.save(volunteer);
        }
        throw new RuntimeException("Volunteer not found with id: " + id);
    }

    @Override
    public void deleteVolunteer(Integer id) {
        volunteerRepository.deleteById(id);
    }
} 