package agenda_solidaria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import agenda_solidaria.model.Resource;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    
    List<Resource> findByEventId(Long eventId);
    
    List<Resource> findByDescriptionContainingIgnoreCase(String description);
} 