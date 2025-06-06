package agenda_solidaria.agenda_solidaria.service;

import agenda_solidaria.agenda_solidaria.model.DocumentType;
import java.util.List;
import java.util.Optional;

public interface DocumentTypeService {
    List<DocumentType> getAllDocumentTypes();
    Optional<DocumentType> getDocumentTypeById(Integer id);
    DocumentType createDocumentType(DocumentType documentType);
    DocumentType updateDocumentType(Integer id, DocumentType documentType);
    void deleteDocumentType(Integer id);
} 