package agenda_solidaria.agenda_solidaria.controller;

import agenda_solidaria.agenda_solidaria.model.OrganizationType;
import agenda_solidaria.agenda_solidaria.service.OrganizationTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organization-types")
@Tag(name = "Organization Type", description = "Organization Type management APIs")
public class OrganizationTypeController {

    private final OrganizationTypeService organizationTypeService;

    @Autowired
    public OrganizationTypeController(OrganizationTypeService organizationTypeService) {
        this.organizationTypeService = organizationTypeService;
    }

    @GetMapping
    public ResponseEntity<List<OrganizationType>> getAllOrganizationTypes() {
        return ResponseEntity.ok(organizationTypeService.getAllOrganizationTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationType> getOrganizationTypeById(@PathVariable Integer id) {
        return organizationTypeService.getOrganizationTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrganizationType> createOrganizationType(@RequestBody OrganizationType organizationType) {
        return ResponseEntity.ok(organizationTypeService.createOrganizationType(organizationType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrganizationType> updateOrganizationType(@PathVariable Integer id, @RequestBody OrganizationType organizationType) {
        try {
            return ResponseEntity.ok(organizationTypeService.updateOrganizationType(id, organizationType));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganizationType(@PathVariable Integer id) {
        organizationTypeService.deleteOrganizationType(id);
        return ResponseEntity.ok().build();
    }
} 