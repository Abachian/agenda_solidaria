package ar.com.vwa.extranet.services.security;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import com.google.common.collect.Lists;

public class CompositeAuthenticationProvider implements AuthenticationProvider {
	
	private static Logger logger = LoggerFactory.getLogger(CompositeAuthenticationProvider.class);
	
	private List<AuthenticationProvider> providers;
	
	public CompositeAuthenticationProvider(AuthenticationProvider... providers) {
		super();
		this.providers = Lists.newArrayList(providers);
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		AuthenticationException lastException = null;
		Authentication auth = null;
		
		for (AuthenticationProvider authenticationProvider : providers) {
			logger.info("Auth " + authenticationProvider.toString());
			
			try {
				auth = authenticationProvider.authenticate(authentication);
				logger.info("Auth response {}", auth);
				if(auth != null)
					return auth;
			} catch (AuthenticationException ex) {
				logger.error("Error en {} - {}", authenticationProvider.toString(), ex.getMessage());
				
				lastException = ex;
			}
		}
		
		if(lastException != null) throw lastException;
		else return null;
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	public void setProviders(List<AuthenticationProvider> providers) {
		this.providers = providers;
	}

}
