package agenda_solidaria.repository;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import agenda_solidaria.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Optional<User> findByUsernameIgnoreCase(String username);

    boolean existsByEmailAndEnabledNot(@NotEmpty String email, @NotEmpty boolean enabled);

    boolean existsByUsernameIgnoreCase(@NotEmpty String username);
} 