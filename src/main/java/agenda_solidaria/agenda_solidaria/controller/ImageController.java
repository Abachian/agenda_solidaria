package agenda_solidaria.agenda_solidaria.controller;

import agenda_solidaria.agenda_solidaria.model.Image;
import agenda_solidaria.agenda_solidaria.service.ImageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@Tag(name = "Image", description = "Image management APIs")
public class ImageController {

    @Autowired
    private final ImageService imageService;

    @GetMapping
    public ResponseEntity<List<Image>> getAllImages() {
        return ResponseEntity.ok(imageService.getAllImages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Image> getImageById(@PathVariable Integer id) {
        return imageService.getImageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Image> createImage(@RequestBody Image image) {
        return ResponseEntity.ok(imageService.createImage(image));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Image> updateImage(@PathVariable Integer id, @RequestBody Image image) {
        try {
            return ResponseEntity.ok(imageService.updateImage(id, image));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Integer id) {
        imageService.deleteImage(id);
        return ResponseEntity.ok().build();
    }
} 