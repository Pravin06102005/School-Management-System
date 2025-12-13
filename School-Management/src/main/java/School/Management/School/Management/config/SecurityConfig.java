package School.Management.School.Management.config;

import School.Management.School.Management.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(cs -> cs.disable())
                .cors(cors -> {}) // CORS is now handled by WebConfig.java
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                
                .authorizeHttpRequests(auth -> auth
                        // 1. Authentication Endpoints (Registration/Login) - Accessible to all
                        .requestMatchers("/api/auth/**").permitAll()
                        
                        // 2. Dashboard Data Endpoints - Require ADMIN role (Fixes 400 Errors)
                        // These are the specific endpoints hit by your dashboard's loadCounts/loadStandardDistribution
                        .requestMatchers(
                            "/api/standards", 
                            "/api/divisions",
                            "/api/students",
                            "/api/staff",
                            "/api/academic-years"
                        ).hasRole("ADMIN")
                        
                        // 3. Log/Activity Endpoint - Require ADMIN role (Fixes 403 Forbidden)
                        .requestMatchers("/api/logs/**").hasRole("ADMIN")
                        
                        // 4. Admin Management Endpoints - Require ADMIN role (Profile/Settings)
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // 5. All Other Requests MUST be authenticated (require a valid JWT token)
                        .anyRequest().authenticated()
                )

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }
}
