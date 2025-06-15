package agenda_solidaria.service;

import agenda_solidaria.model.Organization;
import java.util.List;
import java.util.Optional;

public interface OrganizationService {
    List<Organization> getAllOrganizations();
    Optional<Organization> getOrganizationById(Long id);
    Organization createOrganization(Organization organization);
    Organization updateOrganization(Long id, Organization organization);
    void deleteOrganization(Long id);
} 