package fast.campus.authservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EncryptService {
    private final PasswordEncoder passwordEncoder;

    public String encrypt(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * @param source Encrypt 처리되어 DB에 저장 되어 있는 비밀번호
     * @param target 비교하고자 하는 비밀번호
     * @return 일치 여부
     */
    public boolean matches(String source, String target) {
        return passwordEncoder.matches(source, target);
    }
}
