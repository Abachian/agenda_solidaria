package agenda_solidaria.security;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {
    private final GoogleAuthenticator gAuth;
    private final ConcurrentHashMap<String, String> otpSecrets;

    public OtpService() {
        this.gAuth = new GoogleAuthenticator();
        this.otpSecrets = new ConcurrentHashMap<>();
    }

    /**
     * Genera un nuevo código OTP para un usuario
     * 
     * @param requestId
     * @return
     */
    public String generateOtp(String requestId) {
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        otpSecrets.put(requestId, key.getKey());
        return GoogleAuthenticatorQRGenerator.getOtpAuthURL("AgendaSolidaria", requestId, key);
    }
    
    /**
     * Valida un código OTP
     * 
     * @param requestId
     * @param code
     * @return
     */
    public boolean validateOtp(String requestId, String code) {
        String secret = otpSecrets.get(requestId);
        if (secret == null) {
            return false;
        }
        return gAuth.authorize(secret, Integer.parseInt(code));
    }
    
    /**
     * Elimina el código OTP de un usuario
     * 
     * @param requestId
     */
    public void removeOtp(String requestId) {
        otpSecrets.remove(requestId);
    }
} 