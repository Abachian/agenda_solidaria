package agenda_solidaria.agenda_solidaria.service.impl;

import agenda_solidaria.agenda_solidaria.model.Event;
import agenda_solidaria.agenda_solidaria.repository.EventRepository;
import agenda_solidaria.agenda_solidaria.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> getEventById(Integer id) {
        return eventRepository.findById(id);
    }

    @Override
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Integer id, Event event) {
        if (eventRepository.existsById(id)) {
            event.setId(id);
            return eventRepository.save(event);
        }
        throw new RuntimeException("Event not found with id: " + id);
    }

    @Override
    public void deleteEvent(Integer id) {
        eventRepository.deleteById(id);
    }

    @Override
    public List<Event> getEventsByOrganization(Integer organizationId) {
        return eventRepository.findByOrganizationId(organizationId);
    }

    @Override
    public List<Event> getEventsByType(Integer eventTypeId) {
        return eventRepository.findByEventTypeId(eventTypeId);
    }
} 