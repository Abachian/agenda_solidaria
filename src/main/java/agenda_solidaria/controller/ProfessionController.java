package agenda_solidaria.controller;

import agenda_solidaria.model.Profession;
import agenda_solidaria.service.ProfessionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professions")
@Tag(name = "Profession", description = "Profession management APIs")
public class ProfessionController {

    @Autowired
    private ProfessionService professionService;

    @Autowired
    public ProfessionController(ProfessionService professionService) {
        this.professionService = professionService;
    }

    @GetMapping
    public ResponseEntity<List<Profession>> getAllProfessions() {
        return ResponseEntity.ok(professionService.getAllProfessions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profession> getProfessionById(@PathVariable Long id) {
        return professionService.getProfessionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Profession> createProfession(@RequestBody Profession profession) {
        return ResponseEntity.ok(professionService.createProfession(profession));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profession> updateProfession(@PathVariable Long id, @RequestBody Profession profession) {
        try {
            return ResponseEntity.ok(professionService.updateProfession(id, profession));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfession(@PathVariable Long id) {
        professionService.deleteProfession(id);
        return ResponseEntity.ok().build();
    }
} 