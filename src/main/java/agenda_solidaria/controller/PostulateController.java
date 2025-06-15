package agenda_solidaria.controller;

import agenda_solidaria.model.Postulate;
import agenda_solidaria.service.PostulateService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/postulates")
@Tag(name = "Postulate", description = "Postulate management APIs")
public class PostulateController {

    @Autowired
    private PostulateService postulateService;

    @GetMapping
    public ResponseEntity<List<Postulate>> getAllPostulates() {
        return ResponseEntity.ok(postulateService.getAllPostulates());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Postulate> getPostulateById(@PathVariable Long id) {
        return postulateService.getPostulateById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Postulate> createPostulate(@RequestBody Postulate postulate) {
        return ResponseEntity.ok(postulateService.createPostulate(postulate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Postulate> updatePostulate(@PathVariable Long id, @RequestBody Postulate postulate) {
        try {
            return ResponseEntity.ok(postulateService.updatePostulate(id, postulate));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostulate(@PathVariable Long id) {
        postulateService.deletePostulate(id);
        return ResponseEntity.ok().build();
    }
} 