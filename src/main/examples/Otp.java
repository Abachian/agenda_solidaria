package ar.com.vwa.extranet.domain;

import ar.com.vwa.extranet.services.security.VWUser;

public class Otp {

	private VWUser user;
	private String requestId;
	private String qr;
	private String secretKey;
	
	public VWUser getUser() {
		return user;
	}
	
	public void setUser(VWUser user) {
		this.user = user;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getQr() {
		return qr;
	}

	public void setQr(String qr) {
		this.qr = qr;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

}
