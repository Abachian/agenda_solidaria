package ar.com.vwa.extranet.services;

import java.util.List;
import java.util.Set;

import ar.com.vwa.extranet.domain.*;
import ar.com.vwa.extranet.services.security.VWUser;
import ar.com.vwa.extranet.views.ChangePasswordRequest;
import ar.com.vwa.extranet.views.LoginResponse;
import ar.com.vwa.extranet.views.SaveUsuarioRequest;
import ar.com.vwa.extranet.views.UpdateUsuarioRequest;

public interface UsuarioService {

	/**
	 * Login de usuario
	 * 
	 * @param user
	 * @return
	 */
	LoginResponse login(String username, String password);

	/**
	 * Registra un usuario (identificado por el reqeustId), generando un nuevo codigo OTP
	 * 
	 * @param requestId
	 */
	void registrarOTP(String requestId);

	/**
	 * Elimina los datos de registro para un usuario (identificado por requestId)
	 * 
	 * @param requestId
	 */
	void desregistrarOTP(String requestId);

	/**
	 * Valida el codigo otp para un usuario}
	 * 
	 * @param requestId
	 * @param code
	 * @return 
	 */
	LoggedUserDTO validarOtp(String requestId, String code);

	/**
	 * Crea un {@link Otp} con la info para publicar
	 * @param requestId
	 * @return
	 */
	Otp showSecretKeyQR(String requestId);

	List<Usuario> findAllUsuariosActivos();

	List<VWUser> searchVwUsers(String criterio);

	void createUsuario(SaveUsuarioRequest request);
	
	Usuario getUsuario(String id);

	Usuario getUsuarioById(Long id);

	String deleteUsuario(String id);

	void updateUsuario(String id , UpdateUsuarioRequest request);

	List<RolMenu> getPermisos();

	List<Usuario> findByRoles(Set<Rol> roles);

	Set<Rol> getRoles();

	void changePasswordExterno(ChangePasswordRequest request);

	boolean forgetPassword(String username);

	boolean verifyForgetPassword(String uuid);

	

}
