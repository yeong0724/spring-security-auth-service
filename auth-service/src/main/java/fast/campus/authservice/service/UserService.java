package fast.campus.authservice.service;

import fast.campus.authservice.domain.User;
import fast.campus.authservice.repository.auth.AuthRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final OtpService otpService;

    private final AuthRepository authRepository;
    private final EncryptService encryptService;

    public UserService(OtpService otpService, AuthRepository authRepository, EncryptService encryptService) {
        this.otpService = otpService;
        this.authRepository = authRepository;
        this.encryptService = encryptService;
    }

    public User createNewUser(String userId, String password) {
        return authRepository.createNewUser(new User(userId, password));
    }

    /**
     * Id, Password 검증 후 OtpCode 저장
     * - 기존에 OtpCode 가 없으면 신규 코드 저장, 있으면 갱신
     */
    public String auth(String userId, String password) {
        User user = authRepository.getUserByUserId(userId);

        if (encryptService.matches(password, user.getPassword())) {
            return otpService.renewOtp(userId);
        } else {
            throw new RuntimeException();
        }
    }
}
