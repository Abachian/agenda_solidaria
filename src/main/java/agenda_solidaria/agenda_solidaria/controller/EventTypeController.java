package agenda_solidaria.agenda_solidaria.controller;

import agenda_solidaria.agenda_solidaria.model.EventType;
import agenda_solidaria.agenda_solidaria.service.EventTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event-types")
@Tag(name = "Event Type", description = "Event Type management APIs")
public class EventTypeController {

    private final EventTypeService eventTypeService;

    @Autowired
    public EventTypeController(EventTypeService eventTypeService) {
        this.eventTypeService = eventTypeService;
    }

    @GetMapping
    public ResponseEntity<List<EventType>> getAllEventTypes() {
        return ResponseEntity.ok(eventTypeService.getAllEventTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventType> getEventTypeById(@PathVariable Integer id) {
        return eventTypeService.getEventTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EventType> createEventType(@RequestBody EventType eventType) {
        return ResponseEntity.ok(eventTypeService.createEventType(eventType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventType> updateEventType(@PathVariable Integer id, @RequestBody EventType eventType) {
        try {
            return ResponseEntity.ok(eventTypeService.updateEventType(id, eventType));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventType(@PathVariable Integer id) {
        eventTypeService.deleteEventType(id);
        return ResponseEntity.ok().build();
    }
} 