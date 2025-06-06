package ar.com.vwa.extranet.services.security.ldap;

import java.util.Collections;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.ldap.LdapName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.ldap.support.LdapUtils;

import ar.com.vwa.extranet.services.security.LdapService;
import ar.com.vwa.extranet.services.security.VWUser;

public class ActiveDirectoryLdapService implements LdapService {
	
	private static Logger logger = LoggerFactory.getLogger(ActiveDirectoryLdapService.class);

	private LdapTemplate actualLdapTemplate;
	private LdapTemplate nuevoLdapTemplate;
	
	public ActiveDirectoryLdapService(LdapTemplate actualLdapTemplate, LdapTemplate nuevoLdapTemplate) {
		this.actualLdapTemplate = actualLdapTemplate;
		this.nuevoLdapTemplate = nuevoLdapTemplate;
	}

	@Override
	public List<VWUser> findBy(String criterio) {
		logger.info("Buscando usuarios por criterio {}", criterio);
		
		List<VWUser> users = searchInTemplate(criterio, this.nuevoLdapTemplate);
		if(users.isEmpty()) {
			logger.error("No se encontraron usuario en el ad nuevo, buscando en el viejo...");
			users = searchInTemplate(criterio, this.actualLdapTemplate);
		}
		
		logger.info("Resultados de busqueda de usuarios: {}", users.size());
		return users;
	}

	private List<VWUser> searchInTemplate(String criterio, LdapTemplate ldapTemplate) {
		LdapName usersBaseDn = LdapUtils.newLdapName("OU=Accounts");
		
		return ldapTemplate.search(LdapQueryBuilder.query()
				.base(usersBaseDn)
				.where("sAMAccountName").like(criterio)
				.or("displayName").like(criterio + "*")
				.or("mail").like(criterio),
				new ContextMapper<VWUser>() {
			@Override
			public VWUser mapFromContext(Object ctx) throws NamingException {
				DirContextAdapter context = (DirContextAdapter) ctx;
				
				/*
				String[] memberOf = context.getStringAttributes("memberOf");
				List<GrantedAuthority> authorities = new ArrayList<>();
				
				for (String attr : memberOf) {
					String role = attr.substring(0, attr.indexOf(","));
					authorities.add(new SimpleGrantedAuthority(role));
				}
				*/
				String username = context.getStringAttribute("sAMAccountName");
				VWUser user = new VWUser(username, Collections.emptyList());
				
				user.setFirstName(context.getStringAttribute("givenName"));
				user.setLastName(context.getStringAttribute("sn"));
				user.setCompleteName(context.getStringAttribute("displayName"));
				user.setEmail(context.getStringAttribute("mail"));
				user.setInterno(true);
				
				return user;
			}
		});
	}
}
