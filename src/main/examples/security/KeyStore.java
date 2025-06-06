package ar.com.vwa.extranet.services.security;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyStore {
	
	private static Logger logger = LoggerFactory.getLogger(KeyStore.class);
	
	public static final String RSA = "RSA";
	public static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzmTT6xTHkyRTZMfj3Dgdi+HXGI+5R1z651F30SOJGWViaJIMdTUNzbCMv+qX+L9wbHuq25zts1esg83tMfdyMunOQsZG61HMmkAur0dRSewsy+NhzzuOJrxRBdxGopqchMGmFKgZF023qWO1HwhabTDz8taGr1Pc7AsHMk/uGKv5Z7fNW+au5lqUGcEj3U4Hp7X/jx1DV+gVJkFnMH3Gdho4SOjOe5voEpR2BcvuRA8sVbB4zNQOZRX8ZZ6cBqChXUDi6TFXycRmEFUtHo+NtV+HefJL+CuxqPRcjEh2+KJbZOMzZLejUzp8Sd/2TsVvrOBWlyfmmjcOFyB7TLl81wIDAQAB";
	public static final String PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDOZNPrFMeTJFNkx+PcOB2L4dcYj7lHXPrnUXfRI4kZZWJokgx1NQ3NsIy/6pf4v3Bse6rbnO2zV6yDze0x93Iy6c5CxkbrUcyaQC6vR1FJ7CzL42HPO44mvFEF3EaimpyEwaYUqBkXTbepY7UfCFptMPPy1oavU9zsCwcyT+4Yq/lnt81b5q7mWpQZwSPdTgentf+PHUNX6BUmQWcwfcZ2GjhI6M57m+gSlHYFy+5EDyxVsHjM1A5lFfxlnpwGoKFdQOLpMVfJxGYQVS0ej421X4d58kv4K7Go9FyMSHb4oltk4zNkt6NTOnxJ3/ZOxW+s4FaXJ+aaNw4XIHtMuXzXAgMBAAECggEAToB9NjnmGupDKd75pQZW/qB8rqPj5efQqvEKsKpieB1ey8VZJa9zO5v+PPTerscp3KlA+Fz88CzVuiA2Kr4iWokhYbaGvPSU7MqitxpaKdLByx8GUcsmK0ePTbpVx30lMT1yjQLO4FU4GR6Kgm4r7sIi12ePPNqZ+dHL/+/08L6w19wseYa/q4siFHdfTw0/Br853Jg1I4zvZYuutNA+8KG+X5TG1EQC6dskOHJ5uxoSgO+1bDJicJfYyvTLyefOvlI41P7KB2L2HiG4vDLbf5geMIrqP8taMMEYj46cYOHaAqtBzoAJT6g2gPCzZOIXX4YwH4J70zIPKY2L2fsFSQKBgQD+ABNBBlJdIraoQX2mRSfmcZpzBVMOqseyRmp2vkQUiF1Q4+4WYJIfG/kWkmqvFf65JJIMXmpBFFmr2OS/2dJ38m9Zf9SNFpJvWSzA7i+es7R3O/NSFoTMKe+MAMYr5Cn4xDmeCboTn9TmmwrUBctmyG6KIYlwS7ah0XmQa6YLXQKBgQDQBM3hpnFmP/Xmcma4DpjoBJKnb+tHIkkom5g/p00Y+p6mAImPal2BDeDOta+gIgpFgCeMxcBR89cykDjXP50i74HRzW/nbF+ZL4ta0r9YTNFSRXtTxxPKEF30c9B4FpgB9gtb7Zs/qN1cpvFvQKxunDsAax3oxNqsAvjTp0HZwwKBgQDYyjekyECEPZyo/zy/LUdg6JVqlqDNY/YNswwrTTnOTJVY9THA56vP4ZA4M7/asGs4mn4NEIigAz6F7hlPphp9Re7D9Nb1lM5nOzV3ddQvIDJnkkObTJ0LpL9QP1jlHVi1esWynZq0JTcRnEMhs9BnaarvOrCTqAu5EZEwMrEGAQKBgF4gfmTH4vJIqOVhCNfqSwhZ4V6Ahy8F9aK5XVgYRQuzhAxLm/NYRv05oWsHzXOhU5KFXeAWL2Ml+k938TB9KYaAZ/behe9rG4r+d4leaZT0FZertxV+tJavd0RBD6j3WEsy4Yr0ZlaU+62MIR2dXykh6pTS45LLWMOTCLMFMQfvAoGBAPmlqAiWzrv+D+BvLM5fTuEKkbg/zuI+L2mogVuZbrYNCGT2l2lZQ68T83qPGTgwxoWHy7QZ8BGCOcMMGGIXnMIeRK1K6L84z6RfOqj2WNA/bA7y7xj8Abvxb9fsOU0cVD9geAjOdqgu/42fGEunGgWt7xB6PmmnwYdfiBn/mNil";
	

	private KeyStore() {
	}
	
	public static Key getPrivateKey() {
		try {
			KeyFactory kf = KeyFactory.getInstance(RSA);
	
	        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(
	        		Base64.getDecoder().decode(PRIVATE_KEY));
	        return kf.generatePrivate(keySpecPKCS8);
		} catch(Exception e) {
			logger.error("No se ha podido obtener la clave privada");
			throw new RuntimeException(e);
		}
	}
	
	public static Key getPublicKey() {
		try {
			KeyFactory kf = KeyFactory.getInstance(RSA);
			
			byte[] keyBytes = Base64.getDecoder().decode(
					PUBLIC_KEY.getBytes("utf-8"));
			X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
			return kf.generatePublic(spec);
		} catch(Exception e) {
			logger.error("No se ha podido obtener la clave privada");
			throw new RuntimeException(e);
		}
	}
	
	
	
	public static void main(String[] args) throws Exception {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		
		kpg.initialize(2048);
		KeyPair kp = kpg.generateKeyPair();
		
		Key pub = kp.getPublic();
		Key pvt = kp.getPrivate();
		
		// Get the bytes of the public and private keys
        byte[] privateKeyBytes = pvt.getEncoded();
        byte[] publicKeyBytes = pub.getEncoded();

        // Get the formats of the encoded bytes
        //String formatPrivate = pvt.getFormat(); // PKCS#8
        //String formatPublic = pub.getFormat(); // X.509

        System.out.println("Private Key : " + Base64.getEncoder().encode(privateKeyBytes));
        System.out.println("Public Key : " + Base64.getEncoder().encode((publicKeyBytes)));
        
	}


}
