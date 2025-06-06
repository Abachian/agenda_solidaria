package agenda_solidaria.agenda_solidaria.repository;

import agenda_solidaria.agenda_solidaria.model.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Integer> {
} 