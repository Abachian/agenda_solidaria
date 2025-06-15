package agenda_solidaria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import agenda_solidaria.model.Postulate;

@Repository
public interface PostulateRepository extends JpaRepository<Postulate, Long> {
    List<Postulate> findByVolunteerId(Long volunteerId);
    List<Postulate> findByNeedId(Long needId);
} 