package School.Management.School.Management.config;

import School.Management.School.Management.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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
        //         .csrf(cs -> cs.disable())
        //         .cors(cors -> {}) // ⭐ ENABLE CORS SUPPORT
        //         .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // ⭐ NO JSESSIONID
        //         .authorizeHttpRequests(auth -> auth
        //                 .requestMatchers("/api/auth/**").permitAll()
        //                 .requestMatchers("/api/admin/**").hasRole("ADMIN")
        //                 .requestMatchers("/api/divisions").hasRole("ADMIN")
        //                 .requestMatchers("/api/students").hasRole("ADMIN")
        //                 .anyRequest().authenticated()
        //         )

        //         .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // ⭐ Correct placement

        // return http.build();


            .csrf(cs -> cs.disable())
                .cors(cors -> {}) 
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        
                        // ⭐ UPDATED RULES BELOW THIS LINE ⭐
                        
                        // Grant ADMIN access to dashboard data endpoints (400 Bad Request Fix)
                        .requestMatchers(
                            "/api/standards", 
                            "/api/divisions",
                            "/api/students",
                            "/api/staff"
                        ).hasRole("ADMIN")

                        // Grant ADMIN access to logs (403 Forbidden Fix)
                        .requestMatchers("/api/logs/**").hasRole("ADMIN") // Fix for 403 error

                        // Grant ADMIN access to admin profile/management (already mostly correct)
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // All other requests MUST be authenticated (which means they need a valid token)
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
