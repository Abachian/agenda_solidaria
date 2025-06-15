package agenda_solidaria.security;

import java.util.List;

import agenda_solidaria.dto.ChangePasswordRequest;
import agenda_solidaria.dto.LoginRequest;
import agenda_solidaria.dto.LoginResponse;
import agenda_solidaria.dto.OtpRequest;
import agenda_solidaria.dto.ValidateOtpRequest;
import agenda_solidaria.model.Role;
import agenda_solidaria.model.User;

public interface SecurityService {


    /**
     * Cierra la sesión de un usuario
     * @param token Token JWT a invalidar
     */
    void logout(String token);
    
    /**
     * Autentica un usuario interno
     * 
     * @param username
     * @param password
     * @return
     */

    /**
     * Autentica un usuario externo
     * 
     * @param user
     * @param password
     * @return
     */
    AppUser login(User user, String password);
    
    /**
     * Obtiene el usuario logueado
     * 
     * @return
     */
    AppUser getLoggedUser();
    
    /**
     * Obtiene los permisos del usuario logueado
     * 
     * @return
     */
    List<String> getAuthorities();

    String generateJwtToken(User usuario, List<Role> permisos);

} 