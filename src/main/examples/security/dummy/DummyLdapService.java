package ar.com.vwa.extranet.services.security.dummy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import ar.com.vwa.extranet.config.Environments;
import ar.com.vwa.extranet.services.security.LdapService;
import ar.com.vwa.extranet.services.security.VWUser;

@Service
@Profile(Environments.LOCAL)
public class DummyLdapService implements LdapService {
	
	@Override
	public List<VWUser> findBy(String criterio) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("CN=VWAR-100-G-VWQR-Calidad-Gestor"));
		VWUser vwUser = new VWUser("test1", authorities);
		vwUser.setFirstName("primer");
		vwUser.setLastName("segundo");
		vwUser.setEmail("mail@mail.com");
		vwUser.setInterno(true);
		
		VWUser vwUser2 = new VWUser("test2", authorities);
		vwUser2.setFirstName("primer2");
		vwUser2.setLastName("segundo2");
		vwUser2.setEmail("mail2@mail.com");
		vwUser2.setInterno(true);
		
		VWUser vwUser3 = new VWUser("test3", authorities);
		vwUser3.setFirstName("primer3");
		vwUser3.setLastName("segundo3");
		vwUser3.setEmail("mail3@mail.com");
		vwUser3.setInterno(true);
		
		List<VWUser> asList = Arrays.asList(vwUser, vwUser2, vwUser3);
		List<VWUser> result = new ArrayList<>();
		
		for (VWUser user : asList) {
			if(user.getUsername().contains(criterio.toUpperCase()))
				result.add(user);
		}
		
		return result;
	}

}
