package ar.com.vwa.extranet.services.security;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ar.com.vwa.extranet.domain.Usuario;
import ar.com.vwa.extranet.repositories.UsuarioRepository;

@Component("dbAuthProvider")
public class UsuarioLocalAuthenticationProvider implements AuthenticationProvider {
	
	private static Logger logger = LoggerFactory.getLogger(UsuarioLocalAuthenticationProvider.class);
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Usuario usuario = (Usuario)authentication.getPrincipal();
		
		if(passwordEncoder.matches(authentication.getCredentials().toString(), usuario.getPassword())) {
			VWUser user = new VWUser(usuario.getUsername(), Collections.emptyList());
			
			user.setFirstName(usuario.getNombre());
			user.setLastName(usuario.getApellido());
			user.setCompleteName(usuario.getNombre() + " " + usuario.getApellido());
			user.setEmail(usuario.getEmail());
			user.setInterno(false);
			
			Authentication auth = new UsernamePasswordAuthenticationToken(user,
					authentication.getCredentials().toString(), user.getAuthorities());
			return auth;
		} else {
			logger.error("Se presentaron credenciales invalidas");
			throw new BadCredentialsException("Credenciales invalidas");
		}
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
}
