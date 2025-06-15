package ar.com.vwa.extranet.views;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequest {

	@NotEmpty
	private String username;
	@NotEmpty
	private String password;

}
