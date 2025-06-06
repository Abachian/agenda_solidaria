package agenda_solidaria.agenda_solidaria.service.impl;

import agenda_solidaria.agenda_solidaria.model.DocumentType;
import agenda_solidaria.agenda_solidaria.repository.DocumentTypeRepository;
import agenda_solidaria.agenda_solidaria.service.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final DocumentTypeRepository documentTypeRepository;

    @Autowired
    public DocumentTypeServiceImpl(DocumentTypeRepository documentTypeRepository) {
        this.documentTypeRepository = documentTypeRepository;
    }

    @Override
    public List<DocumentType> getAllDocumentTypes() {
        return documentTypeRepository.findAll();
    }

    @Override
    public Optional<DocumentType> getDocumentTypeById(Integer id) {
        return documentTypeRepository.findById(id);
    }

    @Override
    public DocumentType createDocumentType(DocumentType documentType) {
        return documentTypeRepository.save(documentType);
    }

    @Override
    public DocumentType updateDocumentType(Integer id, DocumentType documentType) {
        if (documentTypeRepository.existsById(id)) {
            documentType.setIdDocument(id);
            return documentTypeRepository.save(documentType);
        }
        throw new RuntimeException("DocumentType not found with id: " + id);
    }

    @Override
    public void deleteDocumentType(Integer id) {
        documentTypeRepository.deleteById(id);
    }
} 