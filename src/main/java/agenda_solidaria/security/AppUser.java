package agenda_solidaria.security;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class AppUser extends User {

	private static final long serialVersionUID = -2950821748911561107L;


	private String email;
	private String firstName;
	private String lastName;

    public AppUser(String name, List<GrantedAuthority> authorities) {
		super(name.toUpperCase(), "", authorities);
	}

	public AppUser(String username, String password, List<GrantedAuthority> authorities) {
		super(username.toUpperCase(), password, authorities);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
