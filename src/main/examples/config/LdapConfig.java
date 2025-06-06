package ar.com.vwa.extranet.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(Environments.CLOUD)
public class LdapConfig {

    @Value("${actualUrl}")
	private String actualUrl;
    @Value("${actualBaseDn}")
    private String actualBaseDn;
    @Value("${actualUsername}")
    private String actualUsername;
    @Value("${actualPassword}")
    private String actualPassword;
	
    @Value("${nuevoUrl}")
    private String nuevoUrl;
    @Value("${nuevoBaseDn}")
	private String nuevoBaseDn;
    @Value("${nuevoUsername}")
    private String nuevoUsername;
    @Value("${nuevoPassword}")
    private String nuevoPassword;

	@Value("${spring.profiles.active}")
	private String activeProfile;
	
	public String getActualUrl() {
		return actualUrl;
	}

	public void setActualUrl(String actualUrl) {
		this.actualUrl = actualUrl;
	}

	public String getActualBaseDn() {
		return actualBaseDn;
	}

	public void setActualBaseDn(String actualBaseDn) {
		this.actualBaseDn = actualBaseDn;
	}

	public String getNuevoUrl() {
		return nuevoUrl;
	}

	public void setNuevoUrl(String nuevoUrl) {
		this.nuevoUrl = nuevoUrl;
	}

	public String getNuevoBaseDn() {
		return nuevoBaseDn;
	}

	public void setNuevoBaseDn(String nuvoBaseDn) {
		this.nuevoBaseDn = nuvoBaseDn;
	}

	public String getActualUsername() {
		return actualUsername;
	}

	public void setActualUsername(String actualUsername) {
		this.actualUsername = actualUsername;
	}

	public String getActualPassword() {
		return actualPassword;
	}

	public void setActualPassword(String actualPassword) {
		this.actualPassword = actualPassword;
	}

	public String getNuevoUsername() {
		return nuevoUsername;
	}

	public void setNuevoUsername(String nuevoUsername) {
		this.nuevoUsername = nuevoUsername;
	}

	public String getNuevoPassword() {
		return nuevoPassword;
	}

	public void setNuevoPassword(String nuevoPassword) {
		this.nuevoPassword = nuevoPassword;
	}
	
}
