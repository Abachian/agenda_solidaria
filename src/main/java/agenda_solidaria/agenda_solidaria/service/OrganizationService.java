package agenda_solidaria.agenda_solidaria.service;

import agenda_solidaria.agenda_solidaria.model.Organization;
import java.util.List;
import java.util.Optional;

public interface OrganizationService {
    List<Organization> getAllOrganizations();
    Optional<Organization> getOrganizationById(Integer id);
    Organization createOrganization(Organization organization);
    Organization updateOrganization(Integer id, Organization organization);
    void deleteOrganization(Integer id);
} 