package fast.campus.movieservice.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class InitAuthFilter extends OncePerRequestFilter {
    private final OtpAuthenticationProvider otpAuthenticationProvider;
    private final UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

    @Value("${jwt.signing-key}")
    private String jwtKey;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String username = request.getHeader("username");
        String password = request.getHeader("password");
        String otp = request.getHeader("otp");

        if (StringUtils.isBlank(otp)) {
            UsernamePasswordAuthentication authentication = new UsernamePasswordAuthentication(username, password);
            usernamePasswordAuthenticationProvider.authenticate(authentication);
        } else {
            Authentication authentication = new OtpAuthentication(username, otp);
            otpAuthenticationProvider.authenticate(authentication);

            /**
             * JWT 생성 코드
             */
            SecretKey secretKey = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));
            String jwt = Jwts.builder()
                    .claim("username", username)
                    .signWith(secretKey)
                    .compact();

            response.setHeader("Authorization", jwt);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/login");
    }
}

