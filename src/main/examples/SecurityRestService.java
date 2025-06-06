package ar.com.vwa.extranet.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import ar.com.vwa.extranet.domain.LoggedUserDTO;
import ar.com.vwa.extranet.domain.Otp;
import ar.com.vwa.extranet.services.SecurityService;
import ar.com.vwa.extranet.services.UsuarioService;
import ar.com.vwa.extranet.services.exceptions.ServiceException;
import ar.com.vwa.extranet.views.ChangePasswordRequest;
import ar.com.vwa.extranet.views.LoginRequest;
import ar.com.vwa.extranet.views.LoginResponse;
import ar.com.vwa.extranet.views.RegistrarOTPRequest;
import ar.com.vwa.extranet.views.SimpleView;
import ar.com.vwa.extranet.views.ValidateCodeRequest;
import io.swagger.v3.oas.annotations.tags.Tag;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("security")
@Tag(name = "security")
public class SecurityRestService {
	
	private static Logger logger = LoggerFactory.getLogger(SecurityRestService.class);

	
	@Autowired
	private SecurityService securityService;
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping("/login")
	public LoginResponse login(@RequestBody @Validated LoginRequest loginData) {
		return this.usuarioService.login(
				loginData.getUsername().toUpperCase(),
				loginData.getPassword());
	}
	
	@PostMapping("/otp/registrar")
	public void registrarTotpCode(@RequestBody RegistrarOTPRequest request) {
		this.usuarioService.registrarOTP(request.getRequestId());
	}
	
	@PostMapping("/otp/desregistrar")
	public void desregistrarTotpCode(@RequestBody RegistrarOTPRequest request) {
		this.usuarioService.desregistrarOTP(request.getRequestId());
	}
	
	@PostMapping("/otp/validar")
	public LoggedUserDTO validareTotpCode(@RequestBody ValidateCodeRequest code) {
		try {
			LoggedUserDTO user = this.usuarioService.validarOtp(code.getRequestId(), code.getCode());
			return user;
		} catch(ServiceException e) {
			logger.error("Error validando OTP", e);
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "El codigo ingresado es invalido");
		}
	}
	
	@GetMapping("/otp/{requestId}")
	public Otp showSecretKeyQR(@PathVariable("requestId") String requestId) {
		Otp otp = this.usuarioService.showSecretKeyQR(requestId);
		return otp;
	}
	
	@PostMapping("/logout")
	public void logout(@RequestHeader("Authorization") String token) {
		this.securityService.logout(token);
	}
	
	@PostMapping("/change-password")
	public void changePasswordExterno(@RequestBody ChangePasswordRequest request) {
		this.usuarioService.changePasswordExterno(request);
	}
	
	@PostMapping("/forget-password/{username}")
	public SimpleView<Boolean> forgetPassword(@PathVariable("username") String username) {
		boolean interno = this.usuarioService.forgetPassword(username);
		return new SimpleView<>(interno);
	}
	
	@GetMapping("/forget-password/{uuid}")
	public SimpleView<Boolean> verifyForgetPassword(@PathVariable("uuid") String uuid) {
		boolean interno = this.usuarioService.verifyForgetPassword(uuid);
		return new SimpleView<>(interno);
	}
	
}
