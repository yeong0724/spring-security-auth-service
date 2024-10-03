package fast.campus.authservice.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class OtpCodeUtil {
    public static String generateOtpCode() {
        try {
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();
            int secureRandomOtpCode = secureRandom.nextInt(900000) + 100000;

            return String.valueOf(secureRandomOtpCode);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
