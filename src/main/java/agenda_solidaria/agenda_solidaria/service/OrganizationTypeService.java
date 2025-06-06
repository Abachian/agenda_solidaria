package agenda_solidaria.agenda_solidaria.service;

import agenda_solidaria.agenda_solidaria.model.OrganizationType;
import java.util.List;
import java.util.Optional;

public interface OrganizationTypeService {
    List<OrganizationType> getAllOrganizationTypes();
    Optional<OrganizationType> getOrganizationTypeById(Integer id);
    OrganizationType createOrganizationType(OrganizationType organizationType);
    OrganizationType updateOrganizationType(Integer id, OrganizationType organizationType);
    void deleteOrganizationType(Integer id);
} 