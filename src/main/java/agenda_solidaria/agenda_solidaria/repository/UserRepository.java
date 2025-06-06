package agenda_solidaria.agenda_solidaria.repository;

import agenda_solidaria.agenda_solidaria.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
} 