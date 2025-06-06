package ar.com.vwa.extranet.services.security;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class VWUser extends User {
	
	private static final long serialVersionUID = -2950821748911561107L;
	
	private String firstName;
	private String lastName;
	private String completeName;

	private String email;
	
	private boolean interno = false;

	public VWUser(String name, List<GrantedAuthority> authorities) {
		super(name.toUpperCase(), "", authorities);
	}
	
	public VWUser(String username, String password, List<GrantedAuthority> authorities) {
		super(username.toUpperCase(), password, authorities);
	}
	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCompleteName() {
		return completeName;
	}

	public void setCompleteName(String completeName) {
		this.completeName = completeName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean isInterno() {
		return interno;
	}
	public void setInterno(boolean interno) {
		this.interno = interno;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
