package ar.com.vwa.extranet.services.security.crypto;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AesCrypter implements Crypter {

	private static byte[] keyBytes = {-29, -30, -93, -73, -78, -92, -116, 124,
		-127, 97, 99, 85, 102, -45, 72, -28};

	@Override
	public byte[] decrypt(byte[] value) {
		try {
			SecretKey key = new SecretKeySpec(keyBytes, "AES");
			
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, key);

			byte[] bs = cipher.doFinal(value);
			return bs;
		} catch (Exception ex) {
			throw new CryptographyException(ex);
		}
	}

	public byte[] encrypt(byte[] value) {
		try {
			SecretKey key = new SecretKeySpec(keyBytes, "AES");
			
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, key);

			byte[] bs = cipher.doFinal(value);
			return bs;
		} catch (Exception ex) {
			throw new CryptographyException(ex);
		}
	}

}
