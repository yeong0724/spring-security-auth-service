package fast.campus.authservice.repository.auth;

import fast.campus.authservice.domain.User;
import fast.campus.authservice.entity.otp.OtpEntity;
import fast.campus.authservice.entity.user.UserEntity;
import fast.campus.authservice.exception.InvalidAuthException;
import fast.campus.authservice.repository.otp.OtpJpaRepository;
import fast.campus.authservice.repository.user.UserJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionOperations;

import java.util.Optional;

@Repository
public class AuthRepository {
    /**
     * TransactionOperations 는 TransactionTemplate 의 상위 인터페이스
     */
    private final OtpJpaRepository otpJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final TransactionOperations readTransactionTemplate;
    private final TransactionOperations writeTransactionTemplate;

    public AuthRepository(
            OtpJpaRepository otpJpaRepository,
            UserJpaRepository userJpaRepository,
            TransactionOperations readTransactionTemplate,
            TransactionOperations writeTransactionTemplate
    ) {
        this.otpJpaRepository = otpJpaRepository;
        this.userJpaRepository = userJpaRepository;
        this.readTransactionTemplate = readTransactionTemplate;
        this.writeTransactionTemplate = writeTransactionTemplate;
    }

    public User createNewUser(User user) {
        return writeTransactionTemplate.execute(status -> {
            Optional<UserEntity> userOptional = userJpaRepository.findUserEntityByUserId(user.getUserId());

            if (userOptional.isPresent()) {
                throw new RuntimeException(String.format("User [%s] already exists", user.getUserId()));
            }

            UserEntity savedUserEntity = userJpaRepository.save(user.toEntity());

            return savedUserEntity.toDomain();
        });
    }

    public User getUserByUserId(String userId) {
        return readTransactionTemplate.execute(status ->
                userJpaRepository.findUserEntityByUserId(userId)
                        .orElseThrow(InvalidAuthException::new)
                        .toDomain()
        );
    }

    public String getOtp(String userId) {
        return readTransactionTemplate.execute(status ->
                otpJpaRepository.findOtpEntityByUserId(userId)
                        .orElseThrow(() -> new RuntimeException(String.format("User [%s] already exists", userId)))
                        .getOtpCode()
        );
    }

    public void updateInsertOtp(String userId, String newOtpCode) {
        writeTransactionTemplate.executeWithoutResult(status -> {
            Optional<OtpEntity> optionalOtpEntity = otpJpaRepository.findOtpEntityByUserId(userId);

            if (optionalOtpEntity.isPresent()) {
                optionalOtpEntity.get().renewOtp(newOtpCode);
            } else {
                otpJpaRepository.save(new OtpEntity(userId, newOtpCode));
            }
        });
    }
}
