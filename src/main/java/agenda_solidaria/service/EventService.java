package agenda_solidaria.service;

import java.util.List;
import java.util.Optional;

import agenda_solidaria.model.Event;

public interface EventService {
    List<Event> getAllEvents();
    Optional<Event> getEventById(Long id);
    Event createEvent(Event event);
    Event updateEvent(Long id, Event event);
    void deleteEvent(Long id);
    List<Event> getEventsByOrganization(Long organizationId);
    List<Event> getEventsByType(Long eventTypeId);
} 