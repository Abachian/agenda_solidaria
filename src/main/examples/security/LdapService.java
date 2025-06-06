package ar.com.vwa.extranet.services.security;

import java.util.List;

public interface LdapService {

	List<VWUser> findBy(String criterio);

}
