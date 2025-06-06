package ar.com.vwa.extranet.services.security.ldap;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.ldap.LdapName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import ar.com.vwa.extranet.services.security.VWUser;

public class LdapAuthenticationProvider implements AuthenticationProvider {
	
	private static Logger logger = LoggerFactory.getLogger(LdapAuthenticationProvider.class);

	private LdapAvailable ldapInfo;
	private LdapTemplate ldapTemplate;
	
	/**
	 * Son los grupos/roles que debe tener el usuario
	 * Si esta vacio/null, no se verifican grupos/roles
	 */
	private String[] authorities;

	public LdapAuthenticationProvider(LdapAvailable info, LdapTemplate ldapTemplate) {
		this.ldapInfo = info;
		this.ldapTemplate = ldapTemplate;
	}
	
	public LdapAuthenticationProvider(LdapAvailable info, LdapTemplate ldapTemplate, String... authorities) {
		this.ldapInfo = info;
		this.ldapTemplate = ldapTemplate;
		this.authorities = authorities;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		logger.info("Autenticando {} contra LDAP: {}", username, ldapInfo);
		
		// 1. Autenticacion
		Filter filter = new EqualsFilter("sAMAccountName", username);
		LdapName usersBaseDn = LdapUtils.newLdapName("OU=Accounts");
		
		Boolean authenticate = ldapTemplate.authenticate(usersBaseDn, filter.encode(),
				authentication.getCredentials().toString());
		if (authenticate) {
			VWUser user = ldapTemplate.searchForObject(LdapQueryBuilder.query().base(usersBaseDn).where("sAMAccountName").is(username),
					new ContextMapper<VWUser>() {
				@Override
				public VWUser mapFromContext(Object ctx) throws NamingException {
					DirContextAdapter context = (DirContextAdapter) ctx;
					
					String[] memberOf = context.getStringAttributes("memberOf");
					List<GrantedAuthority> authorities = new ArrayList<>();
					
					for (String attr : memberOf) {
						String role = attr.substring(0, attr.indexOf(","));
						authorities.add(new SimpleGrantedAuthority(role));
					}
					
					
					VWUser user = new VWUser(username, authorities);
					
					user.setFirstName(context.getStringAttribute("givenName"));
					user.setLastName(context.getStringAttribute("sn"));
					user.setCompleteName(context.getStringAttribute("displayName"));
					user.setEmail(context.getStringAttribute("mail"));
					user.setInterno(true);
					
					logger.info("Usuario autenticado {}", user);
					return user;
				}
			});
			
			// 2. Se verifican permisos (si corresponde)
			if(this.authorities != null && this.authorities.length > 0) {
				logger.info("Verificando permisos (requeridos: {}) del usuario {}.", this.authorities, username);

				boolean authority = false;
				root: for(GrantedAuthority auth : user.getAuthorities()) {
					for (String myAuth : authorities) {
						if(auth.getAuthority().toUpperCase().startsWith(myAuth.toUpperCase())) {
							authority = true;
							break root;
						}
					}
				}
				
				if(!authority) {
					logger.error("El usuario no tiene los permisos/grupos/roles necesarios");
					throw new BadCredentialsException("No tiene autorizacion");
				}
			}
			
			logger.info("{} ha sido autenticado con exito.", username);
			Authentication auth = new UsernamePasswordAuthenticationToken(user,
					authentication.getCredentials().toString(), user.getAuthorities());
			return auth;
		} else {
			logger.error("Se presentaron credenciales invalidas o el usuario no existe en el LDAP: " + ldapInfo);
			throw new BadCredentialsException("Credenciales invalidas");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	@Override
	public String toString() {
		return "ldap-authenticator-" + ldapInfo.toString();
	}

}
