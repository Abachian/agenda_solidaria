package agenda_solidaria.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import agenda_solidaria.model.Resource;
import agenda_solidaria.repository.ResourceRepository;
import agenda_solidaria.service.ResourceService;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Override
    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    @Override
    public Optional<Resource> getResourceById(Long id) {
        return resourceRepository.findById(id);
    }

    @Override
    public List<Resource> getResourcesByEventId(Long eventId) {
        return resourceRepository.findByEventId(eventId);
    }

    @Override
    public List<Resource> getResourcesByDescription(String description) {
        return resourceRepository.findByDescriptionContainingIgnoreCase(description);
    }

    @Override
    public Resource saveResource(Resource resource) {
        return resourceRepository.save(resource);
    }

    @Override
    public Optional<Resource> updateResource(Long id, Resource resource) {
        Optional<Resource> resourceOptional = resourceRepository.findById(id);
        if (resourceOptional.isPresent()) {
            Resource updated = resourceOptional.get();
            if (resource.getDescription() != null) updated.setDescription(resource.getDescription());
            if (resource.getQuantity() != null) updated.setQuantity(resource.getQuantity());
            if (resource.getEvent() != null) updated.setEvent(resource.getEvent());
            return Optional.of(resourceRepository.save(updated));
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteResource(Long id) {
        if (resourceRepository.existsById(id)) {
            resourceRepository.deleteById(id);
            return true;
        }
        return false;
    }
} 