package agenda_solidaria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import agenda_solidaria.model.Need;

@Repository
public interface NeedRepository extends JpaRepository<Need, Long> {

    List<Need> findByEventId(Long eventId);

} 