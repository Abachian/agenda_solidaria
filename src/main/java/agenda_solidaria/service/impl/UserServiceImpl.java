package agenda_solidaria.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import agenda_solidaria.dto.ChangePasswordRequest;
import agenda_solidaria.dto.CreateUserRequestDto;
import agenda_solidaria.dto.LoginResponse;
import agenda_solidaria.model.Role;
import agenda_solidaria.security.AppUser;
import agenda_solidaria.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import agenda_solidaria.model.User;
import agenda_solidaria.repository.UserRepository;
import agenda_solidaria.security.Otp;
import agenda_solidaria.security.OtpService;
import agenda_solidaria.security.SecurityService;
import agenda_solidaria.service.UserService;
import agenda_solidaria.service.exceptions.ServiceException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private SecurityService securityService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);





    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public void createUser(CreateUserRequestDto request) {
        boolean emailAlreadyExists = userRepository.existsByEmailAndEnabledNot(request.getEmail(), true);
        boolean passwordAlreadyExists = userRepository.existsByEmailAndEnabledNot(request.getEmail(), false);
        boolean usernameAlreadyExists = userRepository.existsByUsernameIgnoreCase(request.getUsername());
        if (emailAlreadyExists) {
            throw ServiceException.badRequestError("email ya usado");
        }
        if (passwordAlreadyExists) {
            throw ServiceException.badRequestError("password ya usado");
        }
        if (usernameAlreadyExists) {
            throw ServiceException.badRequestError("username ya usado");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEnabled(true);
        user.setRole(request.getRole());
        user.setLoginIntentos(0);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User updatedUser = userOptional.get();
            if (user.getUsername() != null) updatedUser.setUsername(user.getUsername());
            if (user.getPassword() != null) updatedUser.setPassword(user.getPassword());
            if (user.getEmail() != null) updatedUser.setEmail(user.getEmail());
            if (user.getFirstName() != null) updatedUser.setFirstName(user.getFirstName());
            if (user.getLastName() != null) updatedUser.setLastName(user.getLastName());
            if (user.getRole() != null) updatedUser.setRole(user.getRole());
            if (user.getEnabled() != null) updatedUser.setEnabled(user.getEnabled());
            if (user.getLoginIntentos() != null) updatedUser.setLoginIntentos(user.getLoginIntentos());
            if (user.getPhones() != null) updatedUser.setPhones(user.getPhones());
            return userRepository.save(updatedUser);
        }
        throw new RuntimeException("User not found with id: " + id);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    @Override
    public LoginResponse login(String email, String password) {
        Optional<User> oUser = this.userRepository.findByEmail(email);
        if (oUser.isEmpty()) {
            logger.error("El usuario con email {} no existe en la tabla usuarios", email);
            throw ServiceException.unauthorizedError("Usuario no autorizado");
        }
        User user =  oUser.get();
        if (!user.getEnabled()) {
            throw ServiceException.unauthorizedError("Usuario deshabilitado");
        }

        if (user.getLoginIntentos() >= 3) {
            logger.info("El usuario {} ha realizado 3 intentos incorrectos de login", user.getEmail());
            throw ServiceException.badRequestError("Se han realizado 3 intentos incorrectos de login");
        }
        try {

        AppUser agendaUser = securityService.login(user, password);
        user.clearLogin();
        this.userRepository.save(user);

        return LoginResponse.builder()
                .username(agendaUser.getUsername())
                .token(this.securityService.generateJwtToken(user, List.of(user.getRole())))
                .build();
        }catch (ServiceException se) {
            incrementLoginIntentos(user);
            throw se;
        }

    }
    private void incrementLoginIntentos(User user) {
        user.incrementLoginIntentos();

        if (user.getLoginIntentos() >= 3) {
            logger.warn("Demasiados intentos de login, se bloquea al usuario {}", user.getUsername());
            user.setEnabled(false);
        }

        this.userRepository.save(user);
    }
    
    @Override
    public void changePassword(ChangePasswordRequest request) {
        User user = findByUsername(request.getUsername());
        if (user == null) {
            throw ServiceException.unauthorizedError("Usuario no autorizado");
        }
        
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw ServiceException.unauthorizedError("Contraseña actual incorrecta");
        }
        
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public boolean forgetPassword(String username) {
        Optional<User> oUser = this.userRepository.findByUsernameIgnoreCase(username);
        if (oUser.isEmpty())
            throw ServiceException.notFoundError("");

        User user = oUser.get();

        this.userRepository.save(user);

        //TODO implementar el envio de mail con olvide contraseña
        this.mailService.enviarOlvidePassword(user);

        return true;
    }

//    @Override
//    public boolean verifyForgetPassword(String uuid) {
//        return false;
//    }

//    @Override
//    public void resetPasswordUsuario(String username) {
//        User user = getUser(username);
//
//        String newPassword = passwordRules.generatePassword();
//        this.changePassword(user, newPassword);
//    }

    @Override
    public User getUser(String id) {
        Optional<User> oUsuario = this.userRepository.findByUsernameIgnoreCase(id);
        if (oUsuario.isEmpty())
            throw ServiceException.notFoundError("No existe el usuario " + id);

        return oUsuario.get();
    }
}