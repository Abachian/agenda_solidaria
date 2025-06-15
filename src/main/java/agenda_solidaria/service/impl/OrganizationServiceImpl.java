package agenda_solidaria.service.impl;

import agenda_solidaria.model.Organization;
import agenda_solidaria.repository.OrganizationRepository;
import agenda_solidaria.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    @Override
    public Optional<Organization> getOrganizationById(Long id) {
        return organizationRepository.findById(id);
    }

    @Override
    public Organization createOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }

    @Override
    public Organization updateOrganization(Long id, Organization organization) {
        Optional<Organization> orgOptional = organizationRepository.findById(id);
        if (orgOptional.isPresent()) {
            Organization updatedOrg = orgOptional.get();
            if (organization.getDescription() != null) updatedOrg.setDescription(organization.getDescription());
            if (organization.getDirection() != null) updatedOrg.setDirection(organization.getDirection());
            if (organization.getGmapCoordinate() != null) updatedOrg.setGmapCoordinate(organization.getGmapCoordinate());
            if (organization.getPublishDate() != null) updatedOrg.setPublishDate(organization.getPublishDate());
            if (organization.getContent() != null) updatedOrg.setContent(organization.getContent());
            if (organization.getOrganizationType() != null) updatedOrg.setOrganizationType(organization.getOrganizationType());
            if (organization.getUser() != null) updatedOrg.setUser(organization.getUser());
            if (organization.getLogo() != null) updatedOrg.setLogo(organization.getLogo());
            if (organization.getEvents() != null) updatedOrg.setEvents(organization.getEvents());
            if (organization.getImages() != null) updatedOrg.setImages(organization.getImages());
            return organizationRepository.save(updatedOrg);
        }
        throw new RuntimeException("Organization not found with id: " + id);
    }

    @Override
    public void deleteOrganization(Long id) {
        organizationRepository.deleteById(id);
    }
} 