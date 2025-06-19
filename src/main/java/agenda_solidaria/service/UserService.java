package agenda_solidaria.service;

import java.util.List;
import java.util.Optional;

import agenda_solidaria.dto.ChangePasswordRequest;
import agenda_solidaria.dto.CreateUserRequestDto;
import agenda_solidaria.dto.LoginRequest;
import agenda_solidaria.dto.LoginResponse;
import agenda_solidaria.model.User;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    void createUser(CreateUserRequestDto user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    User findByUsername(String username);

    LoginResponse login(String email, String password);

    void changePassword(ChangePasswordRequest request);

    boolean forgetPassword(String username, String tempPassword);

//    boolean verifyForgetPassword(String uuid);
//
//    void resetPasswordUsuario(String username);

    User getUser(String id);

}