package agenda_solidaria.service;

import java.util.List;
import java.util.Optional;

import agenda_solidaria.model.Resource;

public interface ResourceService {
    
    List<Resource> getAllResources();
    
    Optional<Resource> getResourceById(Long id);
    
    List<Resource> getResourcesByEventId(Long eventId);
    
    List<Resource> getResourcesByDescription(String description);
    
    Resource saveResource(Resource resource);
    
    Optional<Resource> updateResource(Long id, Resource resource);
    
    boolean deleteResource(Long id);
} 