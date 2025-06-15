package agenda_solidaria.service;

import agenda_solidaria.model.OrganizationType;
import java.util.List;
import java.util.Optional;

public interface OrganizationTypeService {
    List<OrganizationType> getAllOrganizationTypes();
    Optional<OrganizationType> getOrganizationTypeById(Long id);
    OrganizationType createOrganizationType(OrganizationType organizationType);
    OrganizationType updateOrganizationType(Long id, OrganizationType organizationType);
    void deleteOrganizationType(Long id);
} 