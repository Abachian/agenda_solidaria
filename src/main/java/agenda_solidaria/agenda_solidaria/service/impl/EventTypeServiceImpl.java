package agenda_solidaria.agenda_solidaria.service.impl;

import agenda_solidaria.agenda_solidaria.model.EventType;
import agenda_solidaria.agenda_solidaria.repository.EventTypeRepository;
import agenda_solidaria.agenda_solidaria.service.EventTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EventTypeServiceImpl implements EventTypeService {

    private final EventTypeRepository eventTypeRepository;

    @Autowired
    public EventTypeServiceImpl(EventTypeRepository eventTypeRepository) {
        this.eventTypeRepository = eventTypeRepository;
    }

    @Override
    public List<EventType> getAllEventTypes() {
        return eventTypeRepository.findAll();
    }

    @Override
    public Optional<EventType> getEventTypeById(Integer id) {
        return eventTypeRepository.findById(id);
    }

    @Override
    public EventType createEventType(EventType eventType) {
        return eventTypeRepository.save(eventType);
    }

    @Override
    public EventType updateEventType(Integer id, EventType eventType) {
        if (eventTypeRepository.existsById(id)) {
            eventType.setIdEventType(id);
            return eventTypeRepository.save(eventType);
        }
        throw new RuntimeException("EventType not found with id: " + id);
    }

    @Override
    public void deleteEventType(Integer id) {
        eventTypeRepository.deleteById(id);
    }
} 