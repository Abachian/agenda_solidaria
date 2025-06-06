package agenda_solidaria.agenda_solidaria.repository;

import agenda_solidaria.agenda_solidaria.model.Need;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NeedRepository extends JpaRepository<Need, Integer> {
} 