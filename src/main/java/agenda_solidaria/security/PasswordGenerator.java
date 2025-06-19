package agenda_solidaria.security;

import java.security.SecureRandom;

public class PasswordGenerator {
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String OTHER_CHAR = "!@#$%&*()_+-=[]?";

    private static final int DEFAULT_PASSWORD_LENGTH = 12;

    public static String generateRandomPassword() {
        String chars = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(DEFAULT_PASSWORD_LENGTH);

        for (int i = 0; i < DEFAULT_PASSWORD_LENGTH; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }
}