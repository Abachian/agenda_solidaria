package agenda_solidaria.agenda_solidaria.controller;

import agenda_solidaria.agenda_solidaria.model.Volunteer;
import agenda_solidaria.agenda_solidaria.service.VolunteerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/volunteers")
@Tag(name = "Volunteer", description = "Volunteer management APIs")
public class VolunteerController {

    @Autowired
    private  VolunteerService volunteerService;

    @GetMapping
    public ResponseEntity<List<Volunteer>> getAllVolunteers() {
        return ResponseEntity.ok(volunteerService.getAllVolunteers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> getVolunteerById(@PathVariable Integer id) {
        return volunteerService.getVolunteerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Volunteer> createVolunteer(@RequestBody Volunteer volunteer) {
        return ResponseEntity.ok(volunteerService.createVolunteer(volunteer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Volunteer> updateVolunteer(@PathVariable Integer id, @RequestBody Volunteer volunteer) {
        try {
            return ResponseEntity.ok(volunteerService.updateVolunteer(id, volunteer));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVolunteer(@PathVariable Integer id) {
        volunteerService.deleteVolunteer(id);
        return ResponseEntity.ok().build();
    }
} 