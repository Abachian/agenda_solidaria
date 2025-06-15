package agenda_solidaria.service;

import agenda_solidaria.model.EventType;
import java.util.List;
import java.util.Optional;

public interface EventTypeService {
    List<EventType> getAllEventTypes();
    Optional<EventType> getEventTypeById(Long id);
    EventType createEventType(EventType eventType);
    EventType updateEventType(Long id, EventType eventType);
    void deleteEventType(Long id);
} 