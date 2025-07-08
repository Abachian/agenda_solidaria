package agenda_solidaria.service.impl;

import agenda_solidaria.dto.CreateVolunteerRequestDto;
import agenda_solidaria.dto.CreateVolunteerResponseDto;
import agenda_solidaria.mapper.VolunteerMapper;
import agenda_solidaria.model.Volunteer;
import agenda_solidaria.repository.VolunteerRepository;
import agenda_solidaria.service.VolunteerService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VolunteerServiceImpl implements VolunteerService {

    @Autowired
    private VolunteerRepository volunteerRepository;

    private final VolunteerMapper volunteerMapper = Mappers.getMapper(VolunteerMapper.class) ;
    @Override
    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    @Override
    public Optional<Volunteer> getVolunteerById(Long id) {
        return volunteerRepository.findById(id);
    }

    @Override
    public CreateVolunteerResponseDto createVolunteer(CreateVolunteerRequestDto volunteer) {
        Volunteer newVolunteer = volunteerMapper.toEntity(volunteer);

        return volunteerRepository.save(newVolunteer);
    }

    @Override
    public Volunteer updateVolunteer(Long id, Volunteer volunteer) {
        Optional<Volunteer> volunteerOptional = volunteerRepository.findById(id);
        if (volunteerOptional.isPresent()) {
            Volunteer updatedVolunteer = volunteerOptional.get();
            if (volunteer.getDocument() != null) updatedVolunteer.setDocument(volunteer.getDocument());
            if (volunteer.getDocumentType() != null) updatedVolunteer.setDocumentType(volunteer.getDocumentType());
            if (volunteer.getUser() != null) updatedVolunteer.setUser(volunteer.getUser());
            if (volunteer.getProfessions() != null) updatedVolunteer.setProfessions(volunteer.getProfessions());
            if (volunteer.getImages() != null) updatedVolunteer.setImages(volunteer.getImages());
            if (volunteer.getPostulates() != null) updatedVolunteer.setPostulates(volunteer.getPostulates());
            return volunteerRepository.save(updatedVolunteer);
        }
        throw new RuntimeException("Volunteer not found with id: " + id);
    }

    @Override
    public void deleteVolunteer(Long id) {
        volunteerRepository.deleteById(id);
    }
} 