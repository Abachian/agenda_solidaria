package ar.com.vwa.extranet.services.security.crypto;

public class CryptographyException extends RuntimeException {

	public CryptographyException(Exception ex) {
		super(ex);
	}

	public CryptographyException(String message) {
		super(message);
	}

}
