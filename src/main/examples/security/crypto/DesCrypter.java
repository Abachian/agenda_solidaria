package ar.com.vwa.extranet.services.security.crypto;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DesCrypter implements Crypter {
	
	private static byte[] keyBytes = new byte[] {
		(byte) 0x4c, (byte) 0xf3, (byte) 0xc0, (byte) 0xeb, 
		(byte) 0x38, (byte) 0xd1, (byte) 0xb1, (byte)0x05 };
	
	private static byte[] ivBytes = new byte[] { 0x4c, 0x01, 
		0x02, 0x03, 0x00, 0x01, 0x00, 0x05 };
	
	@Override
	public byte[] decrypt(byte[] value) {
		SecretKey key = new SecretKeySpec(keyBytes, "DES");
		IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		
		try {
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

			cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
			byte[] bs = cipher.doFinal(value);
			return bs;
		} catch(Exception ex) {
			throw new CryptographyException(ex);
		}
	}
	
	public byte[] encrypt(byte[] value) {
		SecretKey key = new SecretKeySpec(keyBytes, "DES");
		IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		
		try {
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
		
			byte[] bs = cipher.doFinal(value);
			return bs;
		} catch(Exception ex) {
			throw new CryptographyException(ex);
		}
	}
	
}
