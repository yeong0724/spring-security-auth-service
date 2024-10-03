package fast.campus.authservice.service;

import fast.campus.authservice.repository.auth.AuthRepository;
import fast.campus.authservice.util.OtpCodeUtil;
import org.springframework.stereotype.Service;

@Service
public class OtpService {
    private final AuthRepository authRepository;

    public OtpService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public boolean checkOtp(String userId, String sourceOtp) {
        // 사용자에게 현재 발급되어 있는 OTP Code
        String targetOtp = authRepository.getOtp(userId);

        return targetOtp.equals(sourceOtp);
    }

    public String renewOtp(String userId) {
        String newOtp = OtpCodeUtil.generateOtpCode();
        authRepository.updateInsertOtp(userId, newOtp);
        return newOtp;
    }
}
