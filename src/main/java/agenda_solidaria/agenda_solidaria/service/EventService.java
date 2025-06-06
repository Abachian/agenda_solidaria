package agenda_solidaria.agenda_solidaria.service;

import agenda_solidaria.agenda_solidaria.model.Event;
import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> getAllEvents();
    Optional<Event> getEventById(Integer id);
    Event createEvent(Event event);
    Event updateEvent(Integer id, Event event);
    void deleteEvent(Integer id);
} 