package agenda_solidaria.service;

import agenda_solidaria.dto.CreateVolunteerRequestDto;
import agenda_solidaria.dto.CreateVolunteerResponseDto;
import agenda_solidaria.model.Volunteer;
import java.util.List;
import java.util.Optional;

public interface VolunteerService {
    List<Volunteer> getAllVolunteers();
    Optional<Volunteer> getVolunteerById(Long id);
    CreateVolunteerResponseDto createVolunteer(CreateVolunteerRequestDto volunteer);
    Volunteer updateVolunteer(Long id, Volunteer volunteer);
    void deleteVolunteer(Long id);
} 