package agenda_solidaria.security;

import lombok.Data;

@Data
public class Otp {
    private String secretKey;
    private String qrCode;
    private String requestId;
} 