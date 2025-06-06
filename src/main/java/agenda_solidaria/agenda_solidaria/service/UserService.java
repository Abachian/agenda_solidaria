package agenda_solidaria.agenda_solidaria.service;

import agenda_solidaria.agenda_solidaria.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Integer id);
    User createUser(User user);
    User updateUser(Integer id, User user);
    void deleteUser(Integer id);
    User findByUsername(String username);
} 