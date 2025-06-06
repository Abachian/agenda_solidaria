package agenda_solidaria.agenda_solidaria.service.impl;

import agenda_solidaria.agenda_solidaria.model.OrganizationType;
import agenda_solidaria.agenda_solidaria.repository.OrganizationTypeRepository;
import agenda_solidaria.agenda_solidaria.service.OrganizationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrganizationTypeServiceImpl implements OrganizationTypeService {

    private final OrganizationTypeRepository organizationTypeRepository;

    @Autowired
    public OrganizationTypeServiceImpl(OrganizationTypeRepository organizationTypeRepository) {
        this.organizationTypeRepository = organizationTypeRepository;
    }

    @Override
    public List<OrganizationType> getAllOrganizationTypes() {
        return organizationTypeRepository.findAll();
    }

    @Override
    public Optional<OrganizationType> getOrganizationTypeById(Integer id) {
        return organizationTypeRepository.findById(id);
    }

    @Override
    public OrganizationType createOrganizationType(OrganizationType organizationType) {
        return organizationTypeRepository.save(organizationType);
    }

    @Override
    public OrganizationType updateOrganizationType(Integer id, OrganizationType organizationType) {
        if (organizationTypeRepository.existsById(id)) {
            organizationType.setIdOrganizationType(id);
            return organizationTypeRepository.save(organizationType);
        }
        throw new RuntimeException("OrganizationType not found with id: " + id);
    }

    @Override
    public void deleteOrganizationType(Integer id) {
        organizationTypeRepository.deleteById(id);
    }
} 