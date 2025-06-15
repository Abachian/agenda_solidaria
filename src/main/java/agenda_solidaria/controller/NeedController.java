package agenda_solidaria.controller;

import agenda_solidaria.model.Need;
import agenda_solidaria.service.NeedService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/needs")
@Tag(name = "Need", description = "Need management APIs")
public class NeedController {

    @Autowired
    private NeedService needService;

    @GetMapping
    public ResponseEntity<List<Need>> getAllNeeds() {
        return ResponseEntity.ok(needService.getAllNeeds());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Need> getNeedById(@PathVariable Long id) {
        return needService.getNeedById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Need> createNeed(@RequestBody Need need) {
        return ResponseEntity.ok(needService.createNeed(need));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Need> updateNeed(@PathVariable Long id, @RequestBody Need need) {
        try {
            return ResponseEntity.ok(needService.updateNeed(id, need));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNeed(@PathVariable Long id) {
        needService.deleteNeed(id);
        return ResponseEntity.ok().build();
    }
} 