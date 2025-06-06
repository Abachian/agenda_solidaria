package ar.com.vwa.extranet.services.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.vwa.extranet.domain.AccionAuditoria;
import ar.com.vwa.extranet.domain.Configuracion;
import ar.com.vwa.extranet.domain.Estado;
import ar.com.vwa.extranet.domain.LoggedUserDTO;
import ar.com.vwa.extranet.domain.MenuAuditoria;
import ar.com.vwa.extranet.domain.Otp;
import ar.com.vwa.extranet.domain.PasswordHistorial;
import ar.com.vwa.extranet.domain.Proveedor;
import ar.com.vwa.extranet.domain.Rol;
import ar.com.vwa.extranet.domain.RolMenu;
import ar.com.vwa.extranet.domain.Usuario;
import ar.com.vwa.extranet.repositories.ConfiguracionRepository;
import ar.com.vwa.extranet.repositories.PasswordHistorialRepository;
import ar.com.vwa.extranet.repositories.RolMenuRepository;
import ar.com.vwa.extranet.repositories.UsuarioRepository;
import ar.com.vwa.extranet.services.MailService;
import ar.com.vwa.extranet.services.OtpService;
import ar.com.vwa.extranet.services.ProveedorService;
import ar.com.vwa.extranet.services.RolService;
import ar.com.vwa.extranet.services.SecurityService;
import ar.com.vwa.extranet.services.UsuarioService;
import ar.com.vwa.extranet.services.exceptions.ServiceException;
import ar.com.vwa.extranet.services.security.LdapService;
import ar.com.vwa.extranet.services.security.PasswordRules;
import ar.com.vwa.extranet.services.security.VWUser;
import ar.com.vwa.extranet.views.ChangePasswordRequest;
import ar.com.vwa.extranet.views.LoginResponse;
import ar.com.vwa.extranet.views.SaveUsuarioRequest;
import ar.com.vwa.extranet.views.UpdateUsuarioRequest;

@Service
public class DefaultUsuarioService implements UsuarioService {

    private static Logger logger = LoggerFactory.getLogger(DefaultUsuarioService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private OtpService otpService;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private LdapService ldapService;
    @Autowired
    private RolMenuRepository rolMenuRepository;
    @Autowired
    private MailService mailService;
    @Autowired
    private ConfiguracionRepository configuracionRepository;
    @Autowired
    private PasswordRules passwordRules;
    @Autowired
    private PasswordHistorialRepository passwordHistorialRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ProveedorService proveedorService;

    @Value("${extranet.login.auth.mfa.enabled}")
    private Boolean login2FEnabled;
    @Autowired
    private RolService rolService;

	@Autowired
	private DefaultLogAuditoriaService logAuditoriaService;

    @Override
    public LoginResponse login(String username, String password) {

        Optional<Usuario> oUsuario = this.usuarioRepository.findByUsernameIgnoreCase(username);
        if (oUsuario.isEmpty()) {
            logger.error("El usuario {} no existe en la tabla usuarios", username);
            throw ServiceException.unauthorizedError("Usuario no autorizado");
        }

		Usuario usuario = oUsuario.get();
		if (usuario.isBloqueado()) {
			logger.error("Usuario {} lockeado", usuario.getUsername());
			throw ServiceException.unauthorizedError("Usuario no autorizado");
		}


		try {
			// login por tipo de usuario por usuario.getUsuarioInterno()
			VWUser user;
			boolean changePassword = false;
			if(usuario.getUsuarioInterno()) {
				user = this.securityService.loginInterno(username, password);
			} else {
				user = this.securityService.loginExterno(usuario, password);

				changePassword = (usuario.getPasswordModificar() == Boolean.TRUE || usuario.getPasswordModificar() == null) ? true : false;
				if(changePassword == false) {
					// Los días transcurridos desde la fecha de creación del password supera los días indicados en password_dias_vencimiento
					Configuracion configuracion = this.configuracionRepository.findFirstBy();
					if(configuracion == null) {
						logger.warn("No se encuentra el registro en configuracion");
						changePassword = false;
					} else {
				        long diasTranscurridos = ChronoUnit.DAYS.between(usuario.getPasswordCreacion(), LocalDateTime.now());
						if(diasTranscurridos > configuracion.getPasswordDiasVencimiento()) {
							logger.info("Hay transcurrido los dias para el vencimiento de la password");
							changePassword = true;
						}
					}
				}
			}

			usuario.setOtpUuid(UUID.randomUUID().toString());
			usuario.clearLogin();

			this.usuarioRepository.save(usuario);

			return LoginResponse.builder()
					.requestId(usuario.getOtpUuid())
					.changePassword(changePassword)
					.build();

		} catch (ServiceException se) {
			// incrementLoginIntentos(usuario);
			throw se;
		}
	}

	private void incrementLoginIntentos(Usuario usuario) {
		usuario.incrementLoginIntentos();

		if (usuario.getLoginIntentos() >= 5) {
			logger.warn("Demasiados intentos de login, se bloquea al usuario {}", usuario.getUsername());

			usuario.setEstado(Estado.DESHABILITADO);
			usuario.setLoginVencimiento(LocalDateTime.now().plusMinutes(30));
		}

		this.usuarioRepository.save(usuario);
	}

	@Override
	public void registrarOTP(String requestId) {
		Usuario usuario = this.usuarioRepository.findByOtpUuid(requestId);
		if (usuario == null) {
			logger.error("No hay una session creada para {}. No se puede proceder con la registracion", requestId);
			throw ServiceException.badRequestError("No hay pre session iniciada correctamente");
		}

		logger.info("Registrando OTP para user {} con requestId {}", usuario.getUsername(), requestId);

		usuario.setOtpCodigo(this.otpService.generateSecretKey());
		usuario.setOtpValidado(true);
		usuario.setOtpUriVencimiento(LocalDateTime.now().plusHours(1));

		this.usuarioRepository.save(usuario);

		// envia el mail con el secret key
		this.mailService.enviarClaveOTP(usuario);
	}

	@Override
	public void desregistrarOTP(String requestId) {
		Usuario usuario = this.usuarioRepository.findByOtpUuid(requestId);
		if (usuario == null) {
			logger.error("No hay una session creada para {}. No se puede proceder con la registracion", requestId);
			throw ServiceException.badRequestError("No hay pre session iniciada correctamente");
		}

		logger.info("Eliminando registros OTP para user {} con requestId {}", usuario.getUsername(), requestId);

		usuario.setOtpCodigo(this.otpService.generateSecretKey());
		usuario.setOtpValidado(false);
		usuario.setOtpUriVencimiento(null);

		this.usuarioRepository.save(usuario);
	}

	@Override
	public LoggedUserDTO validarOtp(String requestId, String code) {
		Usuario usuario = this.usuarioRepository.findByOtpUuid(requestId);
		if (usuario == null) {
			logger.error("No hay una session creada para {}. No se puede proceder con la registracion", requestId);
			throw ServiceException.badRequestError("No hay pre session iniciada correctamente");
		}

		if (this.login2FEnabled == false) {
			logger.warn("Skipping OTP Validation!!!");
			List<RolMenu> permisos = this.rolMenuRepository.findByRoles(usuario.getRoles());
			String token = this.securityService.generateJwtToken(usuario, permisos);

			return new LoggedUserDTO(token, usuario, permisos);
		}

		if (!usuario.isBloqueado() && usuario.isValidOtp()) {
			logger.info("Validando OTP {} - Code: {} - User: {}", usuario.getOtpCodigo(), code, usuario.getUsername());
			boolean isValid = this.otpService.validateCode(usuario.getOtpCodigo(), code);
			if (isValid) {
				LocalDateTime now = LocalDateTime.now();
				usuario.setFechaUltimoAcceso(now);
				usuario.setOtpUriVencimiento(null);
				usuario.clearLogin();

				this.usuarioRepository.save(usuario);

				List<RolMenu> permisos = this.rolMenuRepository.findByRoles(usuario.getRoles());
				String token = this.securityService.generateJwtToken(usuario, permisos);

				return new LoggedUserDTO(token, usuario, permisos);
			} else {
				incrementLoginIntentos(usuario);

				logger.error("Codigo no validado {}", requestId);
				throw ServiceException.badRequestError("Código incorrecto");
			}
		} else {
			logger.error("Usuario {} bloqueado", usuario.toString());
			throw ServiceException.badRequestError("El usuario esta bloqueado");
		}
	}

	@Override
	public Otp showSecretKeyQR(String requestId) {
		Usuario usuario = this.usuarioRepository.findByOtpUuid(requestId);
		if (usuario == null) {
			logger.error("No hay una session creada para {}. No se puede proceder con la registracion", requestId);
			throw ServiceException.badRequestError("No hay pre session iniciada correctamente");
		}

		if (usuario != null && usuario.isValidOtpVencimiento()) {
			logger.info("URL aun habilitada para registracion: {} - User: {}", requestId, usuario.getUsername());

			Otp otp = new Otp();
			otp.setRequestId(requestId);
			// otp.setUser(user);
			otp.setSecretKey(usuario.getOtpCodigo());
			otp.setQr(this.otpService.generateQrUrl(usuario.getUsername(), usuario.getOtpCodigo()));

			return otp;
		} else {
			throw ServiceException.unauthorizedError("Usuario no valido");
		}
	}

	@Override
	public List<Usuario> findAllUsuariosActivos() {
		//return this.usuarioRepository.findByEstadoIn(Estado.HABILITADO, Estado.DESHABILITADO);
		return this.usuarioRepository.findByNoBaja();
	}

	@Override
	@Transactional
	public void createUsuario(SaveUsuarioRequest request) {
		
		boolean emailAlreadyExists = usuarioRepository.existsByEmailAndEstadoNot(request.getEmail(), Estado.BAJA);
        if (emailAlreadyExists) {
            throw ServiceException.badRequestError("Ya existe un usuario con ese mail");
        }
        
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setEmail(request.getEmail());
        usuario.setUsuarioInterno(request.isUsuarioInterno());
        usuario.setEstado(request.getActivo() ? Estado.HABILITADO : Estado.DESHABILITADO);
        usuario.setUsername(request.getUsername());
        usuario.setPasswordModificar(false);
        usuario.setFechaCreacion(LocalDateTime.now());
        
        List<Rol> roles = rolService.findByIds(request.getRoles());
        usuario.setRoles(new HashSet<>(roles));
		
		if (!request.isUsuarioInterno()) { // Externo
			if (request.getProveedores() == null || request.getProveedores().isEmpty()) {
                throw ServiceException.badRequestError("Debe seleccionar al menos un proveedor");
            }
			
			List<Proveedor> proveedores = proveedorService.findByIds(request.getProveedores());
	        usuario.setProveedores(new HashSet<>(proveedores));
			
	        boolean codigoValido;
	        String codigo;
	        do {
	            codigo = "P" + RandomStringUtils.random(6, true, true).toUpperCase();
	            codigoValido = !usuarioRepository.existsByUsername(codigo);
	        } while (!codigoValido);
            
	        usuario.setUsername(codigo);
            
            String newPassword = passwordRules.generatePassword();
            String newPasswordEncoded = passwordEncoder.encode(newPassword);
            usuario.setPassword(newPasswordEncoded);
            usuario.setPasswordCreacion(LocalDateTime.now());
            usuario.setPasswordModificar(true);
            
            usuarioRepository.save(usuario);
            this.passwordHistorialRepository.save(PasswordHistorial.builder()
                    .password(usuario.getPassword())
                    .passwordCreacion(LocalDateTime.now())
                    .usuario(usuario)
                    .build()
            );
            
            mailService.enviarAltaNuevoUsuario(usuario, newPassword);
        } else { //Usuario Interno
            Optional<Usuario> oUsuario = usuarioRepository.findByUsernameIgnoreCase(request.getUsername());
            if (oUsuario.isPresent() && oUsuario.get().getEstado() != Estado.BAJA)
                throw ServiceException.notFoundError("Ya existe el usuario " + request.getUsername());
            usuario.setEstado(request.getActivo() ? Estado.HABILITADO : Estado.DESHABILITADO);
            usuarioRepository.save(usuario);
        }
		this.logAuditoriaService.createLog(
				MenuAuditoria.ABM_USUARIOS,
				AccionAuditoria.ALTA,
				null,
				usuario,
				null,
				null
		);
	}

	@Override
	@Transactional
	public void updateUsuario(String id , UpdateUsuarioRequest request) {
		Usuario usuario = this.getUsuario(id);
		Usuario usuarioAnterior = new Usuario(usuario);
        if (!usuario.getUsuarioInterno()) {
            String email = request.getEmail();
            // Verifica si el email es válido y ha cambiado antes de actualizarlo
            if (StringUtils.isNotBlank(email) && !email.equals(usuario.getEmail())) {
                // El email ha cambiado, verifica si el nuevo email ya existe en la base de datos
                if (this.usuarioRepository.findByEmail(email) != null) {
                    throw ServiceException.badRequestError("El email ya existe");
                }
                usuario.setEmail(email);
            }
            if(request.getProveedores()!= null && !request.getProveedores().isEmpty()) {
                usuario.setProveedores(new HashSet<>(proveedorService.findByIds(request.getProveedores())));
            }
            if (request.getNombre() != null && !request.getNombre().isEmpty()) {
                usuario.setNombre(request.getNombre());
            }
            if (request.getApellido() != null && !request.getApellido().isEmpty()) {
                usuario.setApellido(request.getApellido());
            }
        }
        if(request.getRoles()!= null && !request.getRoles().isEmpty()) {
            usuario.setRoles(new HashSet<>(rolService.findByIds(request.getRoles())));
        } else
        	usuario.setRoles(Collections.emptySet());
        
        //usuario.setEstado(request.isActivo() ? Estado.HABILITADO : Estado.DESHABILITADO);
        this.usuarioRepository.save(usuario);

		this.logAuditoriaService.createLog(
				MenuAuditoria.ABM_USUARIOS,
				AccionAuditoria.MODIFICACION,
				usuarioAnterior,
				usuario,
				null,
				null
		);
	}

	@Override
	@Transactional
	public void changePasswordExterno(ChangePasswordRequest request) {
		Usuario usuario = null;

		if(StringUtils.isNotEmpty(request.getRequestId()))
			usuario = this.usuarioRepository.findByOtpUuid(request.getRequestId());
		else if(StringUtils.isNotEmpty(request.getUuidForgetPassword())) {
			usuario = this.usuarioRepository.findByPasswordForgetUuid(request.getUuidForgetPassword());
		}

		if (usuario == null) {
			logger.error("No hay una session creada para {}. No se puede proceder con el cambio de passowrd", request.getRequestId());
			throw ServiceException.badRequestError("No hay pre session iniciada correctamente");
		}

		// valida el opt
		if(StringUtils.isNotEmpty(request.getUuidForgetPassword())) {
			boolean isValid = this.otpService.validateCode(usuario.getOtpCodigo(), request.getOtp());
			if(isValid == false)
				throw ServiceException.badRequestError("Los datos suministrados no son validos");

		}
				
		this.changePassword(usuario, request.getPassword());

	}


	public void changePassword(Usuario usuario, String newPassword) {
		logger.info("Verificando reglas para cambio de password...");
		boolean validPassword = this.passwordRules.isValidPassword(newPassword);
		if(validPassword) {
			// que sea diferente a la actual
			if(passwordEncoder.matches(newPassword, usuario.getPassword()))
				throw ServiceException.badRequestError("La password no cumple con las reglas");


			// valida que la password no se de las ultimas 5
			List<PasswordHistorial> historico = this.passwordHistorialRepository.findTop5ByUsuarioOrderByPasswordCreacionDesc(usuario);
			boolean matchInHistorico = historico.stream().anyMatch(old -> passwordEncoder.matches(newPassword, old.getPassword()));
			if(matchInHistorico)
				throw ServiceException.badRequestError("La contraseña no puede ser igual a las últimas 5 utilizadas");



			// todo bien, modifica la password
			String pass = this.passwordEncoder.encode(newPassword);

			if(usuario.getPassword() != null)
				this.passwordHistorialRepository.save(PasswordHistorial.builder()
						.password(usuario.getPassword())
						.passwordCreacion(LocalDateTime.now())
						.usuario(usuario)
						.build()
					);

			usuario.setPassword(pass);
			usuario.setPasswordModificar(false);
			usuario.setPasswordCreacion(LocalDateTime.now());

			this.usuarioRepository.save(usuario);
		} else {
			logger.error("Falla por validacion de reglas basicas en BE");
			throw ServiceException.badRequestError("La password no cumple alguna de las reglas");
		}
	}


	@Override
	@Transactional
	public boolean forgetPassword(String username) {
		Optional<Usuario> oUsuario = this.usuarioRepository.findByUsernameIgnoreCase(username);
		if (oUsuario.isEmpty())
			throw ServiceException.notFoundError("");

		Usuario usuario = oUsuario.get();
		if(usuario.getUsuarioInterno())
			throw ServiceException.badRequestError("");

		usuario.setPasswordForgetUuid(UUID.randomUUID().toString());
		usuario.setPasswordForgetVencimiento(LocalDateTime.now().plusMinutes(60));
		this.usuarioRepository.save(usuario);


		this.mailService.enviarOlvidePassword(usuario);

		return usuario.getUsuarioInterno();
	}
	
	@Override
	public boolean verifyForgetPassword(String uuid) {
		Usuario usuario = this.usuarioRepository.findByPasswordForgetUuid(uuid);
		if (usuario == null) {
			logger.error("No existe un usuario con pedido de cambio de password con el uuid: {}", uuid);
			return false;
		}
		
		if(usuario.getPasswordForgetVencimiento().isBefore(LocalDateTime.now())) {
			logger.error("El pedido de cambio de password vencio con el uuid: {}", uuid);
			return false;
		}
		return true;
	}

    @Override
    public Usuario getUsuario(String id) {
        Optional<Usuario> oUsuario = this.usuarioRepository.findByUsernameIgnoreCase(id);
        if (oUsuario.isEmpty())
            throw ServiceException.notFoundError("No existe el usuario " + id);

        return oUsuario.get();
    }

    @Override
    public Usuario getUsuarioById(Long id) {
        Optional<Usuario> oUsuario = this.usuarioRepository.findById(id);
        if (oUsuario.isEmpty())
            throw ServiceException.notFoundError("No existe el usuario con id " + id);

        return oUsuario.get();
    }

    @Override
    public String deleteUsuario(String id) {
        Usuario usuario = this.getUsuario(id);
		Usuario usuarioAnterior = new Usuario(usuario);
        usuario.setEstado(Estado.BAJA);
        usuario.setOtpValidado(Boolean.FALSE);
        usuario.setOtpCodigo(null);
        usuario.setPassword("");
        usuario.setPasswordModificar(true);

        /*
         * NO BORRA RELACIONES if(usuario.getGruposConcesionarios() != null &&
         * !usuario.getGruposConcesionarios().isEmpty())
         * this.grupoConcesionarioRepository.deleteAll(usuario.getGruposConcesionarios()
         * );
         *
         * if(usuario.getConcesionarios() != null &&
         * !usuario.getConcesionarios().isEmpty())
         * this.concesionarioRepository.deleteAll(usuario.getConcesionarios());
         *
         * if(usuario.getRoles() != null && !usuario.getRoles().isEmpty())
         * this.rolRepository.deleteAll(usuario.getRoles());
         */

        this.usuarioRepository.save(usuario);

		this.logAuditoriaService.createLog(
				MenuAuditoria.ABM_USUARIOS,
				AccionAuditoria.BAJA,
				usuarioAnterior,
				null,
				null,
				null
		);

        return usuario.getUsername();
    }

    @Override
    public List<VWUser> searchVwUsers(String criterio) {
        return this.ldapService.findBy(criterio);

    }

    @Override
    public List<RolMenu> getPermisos() {
        VWUser loggedUser = this.securityService.getLoggedUser();

        Usuario usuario = getUsuario(loggedUser.getUsername());

        List<RolMenu> permisos = this.rolMenuRepository.findByRoles(usuario.getRoles());
        return permisos;
    }

    @Override
    public List<Usuario> findByRoles(Set<Rol> roles) {
        List<Usuario> users = new ArrayList<>();
        for (Rol rol : roles) {
            List<Usuario> usuarios = this.usuarioRepository.findByRol(rol);
            if (usuarios.isEmpty() == false) {
                users.addAll(usuarios);
            }
        }

        return users;
    }

    @Override
    public Set<Rol> getRoles() {
        VWUser loggedUser = this.securityService.getLoggedUser();
        Usuario usuario = getUsuario(loggedUser.getUsername());
        Set<Rol> roles = usuario.getRoles();
        return roles;
    }

}
