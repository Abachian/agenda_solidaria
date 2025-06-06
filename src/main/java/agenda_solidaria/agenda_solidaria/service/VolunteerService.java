package agenda_solidaria.agenda_solidaria.service;

import agenda_solidaria.agenda_solidaria.model.Volunteer;
import java.util.List;
import java.util.Optional;

public interface VolunteerService {
    List<Volunteer> getAllVolunteers();
    Optional<Volunteer> getVolunteerById(Integer id);
    Volunteer createVolunteer(Volunteer volunteer);
    Volunteer updateVolunteer(Integer id, Volunteer volunteer);
    void deleteVolunteer(Integer id);
} 