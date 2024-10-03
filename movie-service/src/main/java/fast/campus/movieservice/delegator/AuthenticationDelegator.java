package fast.campus.movieservice.delegator;

import fast.campus.movieservice.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Auth-Service 에 대한 기능 호출
 */
@Component
public class AuthenticationDelegator {
    private final RestTemplate restTemplate;

    public AuthenticationDelegator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${base-url.auth-service}")
    private String authServiceBaseUrl;

    public void restAuth(String userId, String password) {
        String url = authServiceBaseUrl + "/user/auth";

        User user = User.builder()
                .userId(userId)
                .password(password)
                .build();

        /**
         * API url / Request Body / Response Object
         */
        restTemplate.postForEntity(url, new HttpEntity<>(user), Void.class);
    }

    public boolean restOtp(String userId, String otp) {
        String url = authServiceBaseUrl + "/otp/check";

        User user = User.builder()
                .userId(userId)
                .otp(otp)
                .build();

        ResponseEntity<Boolean> response = restTemplate.postForEntity(url, new HttpEntity<>(user), Boolean.class);
        return Boolean.TRUE.equals(response.getBody());
    }
}
