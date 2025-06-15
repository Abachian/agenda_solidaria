package agenda_solidaria.service;

import agenda_solidaria.model.Volunteer;
import java.util.List;
import java.util.Optional;

public interface VolunteerService {
    List<Volunteer> getAllVolunteers();
    Optional<Volunteer> getVolunteerById(Long id);
    Volunteer createVolunteer(Volunteer volunteer);
    Volunteer updateVolunteer(Long id, Volunteer volunteer);
    void deleteVolunteer(Long id);
} 