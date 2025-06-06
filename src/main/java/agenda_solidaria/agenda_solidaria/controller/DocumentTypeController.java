package agenda_solidaria.agenda_solidaria.controller;

import agenda_solidaria.agenda_solidaria.model.DocumentType;
import agenda_solidaria.agenda_solidaria.service.DocumentTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/document-types")
@Tag(name = "Document Type", description = "Document Type management APIs")
public class DocumentTypeController {

    @Autowired
    private DocumentTypeService documentTypeService;


    @GetMapping
    public ResponseEntity<List<DocumentType>> getAllDocumentTypes() {
        return ResponseEntity.ok(documentTypeService.getAllDocumentTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentType> getDocumentTypeById(@PathVariable Integer id) {
        return documentTypeService.getDocumentTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DocumentType> createDocumentType(@RequestBody DocumentType documentType) {
        return ResponseEntity.ok(documentTypeService.createDocumentType(documentType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentType> updateDocumentType(@PathVariable Integer id, @RequestBody DocumentType documentType) {
        try {
            return ResponseEntity.ok(documentTypeService.updateDocumentType(id, documentType));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumentType(@PathVariable Integer id) {
        documentTypeService.deleteDocumentType(id);
        return ResponseEntity.ok().build();
    }
} 