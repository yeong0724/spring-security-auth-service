package fast.campus.movieservice.config;

import fast.campus.movieservice.security.InitAuthFilter;
import fast.campus.movieservice.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private final InitAuthFilter initAuthFilter;
    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(InitAuthFilter initAuthFilter, JwtAuthFilter jwtAuthFilter) {
        this.initAuthFilter = initAuthFilter;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        // Basic 인증 전
        httpSecurity.addFilterBefore(initAuthFilter, BasicAuthenticationFilter.class);

        // Basic 인증 후
        httpSecurity.addFilterAfter(jwtAuthFilter, BasicAuthenticationFilter.class);

        // 모든 요청에 대해서 인증을 요구
        httpSecurity.authorizeHttpRequests(c -> c.anyRequest().authenticated());

        return httpSecurity.build();
    }
}
