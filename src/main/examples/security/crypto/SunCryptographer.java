package ar.com.vwa.extranet.services.security.crypto;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SunCryptographer implements Cryptographer, Serializable {
	
	private static final long serialVersionUID = 4290064271038050081L;
	
	private Digest defaultDigest = Digest.MD5;
	private Cypher defaultCypher = Cypher.DES;
	
	public byte[] digest(String text) {
		return this.digest(text, defaultDigest);
	}
	
	public byte[] digest(String text, Digest method) {
		try {
			MessageDigest digester = MessageDigest.getInstance(this.getAlgorithm(method));
			return digester.digest(text.getBytes());
		} catch(NoSuchAlgorithmException ex) {
			throw new CryptographyException(ex);
		}
	}
	
	public String digestEncoded(String text) {
		return this.digestEncoded(text, this.defaultDigest);
	}
	
	public String digestEncoded(String text, Digest method) {
		return Base64.getEncoder().encodeToString(this.digest(text, method));
	}
	
	@Override
	public byte[] encrypt(String text) {
		return this.encrypt(text, defaultCypher);
	}
	
	@Override
	public byte[] encrypt(String text, Cypher method) {
		switch (method) {
		case DES:
			return new DesCrypter().encrypt(text.getBytes());
		case AES:
			return new AesCrypter().encrypt(text.getBytes());
		default:
			throw new CryptographyException("Algorithm not supported:" + method);
		}
	}
	
	@Override
	public String encryptEncoded(String text) {
		return Base64.getEncoder().encodeToString(this.encrypt(text));
	}
	
	@Override
	public String encryptEncoded(String text, Cypher method) {
		return Base64.getEncoder().encodeToString(this.encrypt(text, method));
	}
	
	@Override
	public String decrypt(byte[] encrypt) {
		return this.decrypt(encrypt, defaultCypher);
	}
	
	@Override
	public String decrypt(byte[] encrypt, Cypher method) {
		byte[] ret;
		switch (method) {
		case DES:
			ret = new DesCrypter().decrypt(encrypt);
			break;
		case AES:
			ret = new AesCrypter().decrypt(encrypt);
			break;
		default:
			throw new CryptographyException("Algorithm not supported:" + method);
		}
		
		return new String(ret);
	}
	
	@Override
	public String decryptEncoded(String text) {
		return this.decryptEncoded(text, this.defaultCypher);
	}
	
	@Override
	public String decryptEncoded(String text, Cypher method) {
		try {
			byte[] value = Base64.getDecoder().decode(text);
			return this.decrypt(value, method);
		} catch(Exception ex) {
			throw new CryptographyException(ex);
		}
	}
	
	private String getAlgorithm(Digest method) throws NoSuchAlgorithmException {
		switch(method) {
		case MD5:
			return "md5";
		case SHA1:
			return "sha-1";
		default:
			throw new NoSuchAlgorithmException();
		}
	}
	
	public static void main(String[] args) {
		if(args.length < 2) {
			System.err.println("Using: java ar.com.exeo.jrca.support.security.SunCryptographer [text] [DES|AES]");
			return;
		}
		
		System.out.println("Encrypting " + args[0] + " using " + args[1]);
		
		Cypher cypher = Cypher.valueOf(args[1]);

		SunCryptographer cryptographer = new SunCryptographer();
		String encrypt = cryptographer.encryptEncoded(args[0], cypher);
		System.out.println("Encripted text: " + encrypt);
	}

}
