package ar.com.vwa.extranet.services.security.crypto;


public interface Cryptographer {
	
	public enum Digest {
		MD5, SHA1;
	}
	
	public enum Cypher {
		DES, AES;
	}
	
	public byte[] digest(String text);
	
	public byte[] digest(String text, Digest method);
	
	public String digestEncoded(String text);
	
	public String digestEncoded(String text, Digest method);
	
	/**
	 * Encrypt the text using a default algorithm
	 */
	public byte[] encrypt(String text);
	
	/**
	 * Encrypt the text using a specific algorithm
	 */
	public byte[] encrypt(String text, Cypher method);
	
	/**
	 * Encrypt (using Base64 encoded) the text using a default algorithm.
	 */
	public String encryptEncoded(String text);
	
	/**
	 * Encrypt (using Base64 encoded) the text using a specific algorithm
	 */
	public String encryptEncoded(String text, Cypher method);
	
	/**
	 * Decrypt the byte array using a default algorithm
	 */
	public String decrypt(byte[] encrypt);
	
	/**
	 * Decrypt the byte array using a specific algorithm
	 */
	public String decrypt(byte[] encrypt, Cypher method);
	
	/**
	 * Decrypt the encoded text using a default algorithm
	 */
	public String decryptEncoded(String text);
	
	/**
	 * Decrypt the encoded text using a specific algorithm
	 */
	public String decryptEncoded(String text, Cypher method);
	
}
