package ar.com.vwa.extranet.services.security.dummy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import ar.com.vwa.extranet.services.security.VWUser;

public class DummyAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		
		if(username.equals("error"))
			throw new BadCredentialsException("Error");
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		//authorities.add(new SimpleGrantedAuthority("CN=VWAR-100-G-VWQR-Calidad-Gestor"));
		//authorities.add(new SimpleGrantedAuthority("ROLE_1"));
		
		VWUser user = new VWUser(username, Collections.emptyList());
		user.setFirstName("Bruce");
		user.setLastName("Harris");
		user.setEmail("evil@heaven.com");
		user.setInterno(true);
		
		Authentication auth = new UsernamePasswordAuthenticationToken(user,
				authentication.getCredentials().toString(), user.getAuthorities());
		return auth;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
