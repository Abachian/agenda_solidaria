package agenda_solidaria.service.impl;

import agenda_solidaria.model.EventType;
import agenda_solidaria.repository.EventTypeRepository;
import agenda_solidaria.service.EventTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EventTypeServiceImpl implements EventTypeService {

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Autowired
    public EventTypeServiceImpl(EventTypeRepository eventTypeRepository) {
        this.eventTypeRepository = eventTypeRepository;
    }

    @Override
    public List<EventType> getAllEventTypes() {
        return eventTypeRepository.findAll();
    }

    @Override
    public Optional<EventType> getEventTypeById(Long id) {
        return eventTypeRepository.findById(id);
    }

    @Override
    public EventType createEventType(EventType eventType) {
        return eventTypeRepository.save(eventType);
    }

    @Override
    public EventType updateEventType(Long id, EventType eventType) {
        Optional<EventType> eventTypeOptional = eventTypeRepository.findById(id);
        if (eventTypeOptional.isPresent()) {
            EventType updatedEventType = eventTypeOptional.get();
            if (eventType.getDescription() != null) updatedEventType.setDescription(eventType.getDescription());
            return eventTypeRepository.save(updatedEventType);
        }
        throw new RuntimeException("EventType not found with id: " + id);
    }

    @Override
    public void deleteEventType(Long id) {
        eventTypeRepository.deleteById(id);
    }
} 