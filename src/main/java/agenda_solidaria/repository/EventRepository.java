package agenda_solidaria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import agenda_solidaria.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("SELECT e FROM Event e JOIN e.organizations o WHERE o.id = :organizationId")
    List<Event> findByOrganizationId(Long organizationId);

    @Query("SELECT e FROM Event e JOIN e.eventType ev WHERE ev.idEventType = :eventTypeId")
    List<Event> findByEventTypeId(Long eventTypeId);
} 