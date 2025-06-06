package agenda_solidaria.agenda_solidaria.service;

import agenda_solidaria.agenda_solidaria.model.EventType;
import java.util.List;
import java.util.Optional;

public interface EventTypeService {
    List<EventType> getAllEventTypes();
    Optional<EventType> getEventTypeById(Integer id);
    EventType createEventType(EventType eventType);
    EventType updateEventType(Integer id, EventType eventType);
    void deleteEventType(Integer id);
} 