package agenda_solidaria.agenda_solidaria.service.impl;

import agenda_solidaria.agenda_solidaria.model.Organization;
import agenda_solidaria.agenda_solidaria.repository.OrganizationRepository;
import agenda_solidaria.agenda_solidaria.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private final OrganizationRepository organizationRepository;

    @Override
    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    @Override
    public Optional<Organization> getOrganizationById(Integer id) {
        return organizationRepository.findById(id);
    }

    @Override
    public Organization createOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }

    @Override
    public Organization updateOrganization(Integer id, Organization organization) {
        if (organizationRepository.existsById(id)) {
            organization.setId(id);
            return organizationRepository.save(organization);
        }
        throw new RuntimeException("Organization not found with id: " + id);
    }

    @Override
    public void deleteOrganization(Integer id) {
        organizationRepository.deleteById(id);
    }
} 