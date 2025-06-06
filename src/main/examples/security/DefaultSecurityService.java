package ar.com.vwa.extranet.services.security;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ar.com.vwa.extranet.domain.RolMenu;
import ar.com.vwa.extranet.domain.Usuario;
import ar.com.vwa.extranet.repositories.RolMenuRepository;
import ar.com.vwa.extranet.services.SecurityService;
import ar.com.vwa.extranet.services.exceptions.ServiceException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class DefaultSecurityService implements SecurityService {
	
	private static Logger logger = LoggerFactory.getLogger(DefaultSecurityService.class);
	
	@Autowired
	@Qualifier("ldapAuthProvider")
	private AuthenticationProvider ldapAuthenticationProvider;
	@Autowired
	@Qualifier("dbAuthProvider")
	private AuthenticationProvider dbAuthenticationProvider;
	
	@Autowired
	private RolMenuRepository rolMenuRepository;
	
	@Value("${extranet.session.expiration.time}")
	private long sessionExpirationTime;
	
	@Override
	public VWUser loginInterno(String username, String password) {
		try {
			Authentication auth = new UsernamePasswordAuthenticationToken(username, password);
			auth = this.ldapAuthenticationProvider.authenticate(auth);
			
			VWUser user = (VWUser) auth.getPrincipal();
			return user;
		} catch(AuthenticationException e) {
			logger.error("Error al autenticar " + username, e);
			throw ServiceException.unauthorizedError("Credenciales Invalidas");
		}
	}
	
	@Override
	public VWUser loginExterno(Usuario usuario, String password) {
		try {
			Authentication auth = new UsernamePasswordAuthenticationToken(usuario, password);
			auth = this.dbAuthenticationProvider.authenticate(auth);
			
			VWUser user = (VWUser) auth.getPrincipal();
			return user;
		} catch(AuthenticationException e) {
			logger.error("Error al autenticar " + usuario.getUsername(), e);
			throw ServiceException.unauthorizedError("Credenciales Invalidas");
		}
	}

	@Override
	public void logout(String sessionId) {
		SecurityContextHolder.clearContext();
	}
	
	@Override
	public VWUser getLoggedUser() {
		return (VWUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	@Override
	public List<String> getAuthorities() {
		return getLoggedUser().getAuthorities()
			.stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.toList());
	}
	
	public String getLoggedUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	@Override
	public String generateJwtToken(Usuario usuario, List<RolMenu> permisos) {
		String token = Jwts.builder()
				.setSubject(usuario.getUsername())
				//.claim(SecurityConstants.AUTHORITIES, Arrays.asList("BASIC"))
				.claim("name", usuario.getUsername())
				.claim("email", usuario.getEmail())
				.claim("firstName", usuario.getNombre())
				.claim("lastName", usuario.getApellido())
				.claim("permisos", Permission.build(permisos))
				.claim("interno", usuario.getUsuarioInterno())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + sessionExpirationTime))
				.signWith(SignatureAlgorithm.RS512, KeyStore.getPrivateKey())
				.compact();

		return SecurityConstants.BEARER + token;
	}
	
}
