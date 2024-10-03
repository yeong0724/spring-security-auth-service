package fast.campus.authservice.service;

import fast.campus.authservice.domain.User;
import fast.campus.authservice.repository.auth.AuthRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final OtpService otpService;

    private final AuthRepository authRepository;

    public UserService(OtpService otpService, AuthRepository authRepository) {
        this.otpService = otpService;
        this.authRepository = authRepository;
    }

    public User createNewUser(String userId, String password) {
        return authRepository.createNewUser(new User(userId, password));
    }
}
