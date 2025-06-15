package agenda_solidaria.service.impl;

import agenda_solidaria.model.Event;
import agenda_solidaria.repository.EventRepository;
import agenda_solidaria.service.EventService;
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
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Long id, Event event) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isPresent()) {
            Event updatedEvent = eventOptional.get();
            updatedEvent.setDescription(event.getDescription());
            updatedEvent.setEventType(event.getEventType());
            updatedEvent.setDirection(event.getDirection());
            updatedEvent.setEndDate(event.getEndDate());
            updatedEvent.setStartDate(event.getStartDate());
            updatedEvent.setGmapCoordinate(event.getGmapCoordinate());
            updatedEvent.setPublishDate(event.getPublishDate());
            updatedEvent.setTitle(event.getTitle());
            updatedEvent.setIntro(event.getIntro());
            return eventRepository.save(event);
        }
        throw new RuntimeException("Event not found with id: " + id);
    }

    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public List<Event> getEventsByOrganization(Long organizationId) {
        return eventRepository.findByOrganizationId(organizationId);
    }

    @Override
    public List<Event> getEventsByType(Long eventTypeId) {
        return eventRepository.findByEventTypeId(eventTypeId);
    }
} 