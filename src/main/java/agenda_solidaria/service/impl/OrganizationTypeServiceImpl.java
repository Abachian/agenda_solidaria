package agenda_solidaria.service.impl;

import agenda_solidaria.model.OrganizationType;
import agenda_solidaria.repository.OrganizationTypeRepository;
import agenda_solidaria.service.OrganizationTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrganizationTypeServiceImpl implements OrganizationTypeService {

    @Autowired
    private OrganizationTypeRepository organizationTypeRepository;

    @Autowired
    public OrganizationTypeServiceImpl(OrganizationTypeRepository organizationTypeRepository) {
        this.organizationTypeRepository = organizationTypeRepository;
    }

    @Override
    public List<OrganizationType> getAllOrganizationTypes() {
        return organizationTypeRepository.findAll();
    }

    @Override
    public Optional<OrganizationType> getOrganizationTypeById(Long id) {
        return organizationTypeRepository.findById(id);
    }

    @Override
    public OrganizationType createOrganizationType(OrganizationType organizationType) {
        return organizationTypeRepository.save(organizationType);
    }

    @Override
    public OrganizationType updateOrganizationType(Long id, OrganizationType organizationType) {
        Optional<OrganizationType> orgTypeOptional = organizationTypeRepository.findById(id);
        if (orgTypeOptional.isPresent()) {
            OrganizationType updatedOrgType = orgTypeOptional.get();
            if (organizationType.getDescription() != null) updatedOrgType.setDescription(organizationType.getDescription());
            return organizationTypeRepository.save(updatedOrgType);
        }
        throw new RuntimeException("OrganizationType not found with id: " + id);
    }

    @Override
    public void deleteOrganizationType(Long id) {
        organizationTypeRepository.deleteById(id);
    }
} 