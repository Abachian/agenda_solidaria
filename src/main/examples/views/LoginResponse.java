package ar.com.vwa.extranet.views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginResponse {
	
	private String requestId;
	
	private boolean changePassword;

}
