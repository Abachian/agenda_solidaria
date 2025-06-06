package ar.com.vwa.extranet.services.security.crypto;

public interface Crypter {
	
	public byte[] encrypt(byte[] value);
	
	public byte[] decrypt(byte[] value);

}
