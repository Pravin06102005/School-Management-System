package com.GYM.GYM.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // ✅ Password encoder bean
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ Authentication manager bean
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // ✅ Main security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // 🔹 CORS + disable CSRF
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowCredentials(true);
                    config.setAllowedOrigins(List.of(
                            "http://127.0.0.1:5500",
                            "http://localhost:5500",
                            "http://localhost:3000"
                    ));
                    config.setAllowedHeaders(List.of("Origin", "Content-Type", "Accept", "Authorization"));
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    return config;
                }))

                // 🔹 No session — stateless JWT
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 🔹 Define authorization rules
                .authorizeHttpRequests(auth -> auth

                        // 1️⃣ Public - Login/Register
                        .requestMatchers("/api/admin/auth/**").permitAll()
                        .requestMatchers("/uploads/**").permitAll()

                        // 2️⃣ Public - Read-only data for user side
                        .requestMatchers(HttpMethod.GET, "/api/trainers/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/membership/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/equipment/**").permitAll()

                        // 3️⃣ Admin-only for CRUD operations
                        .requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")

                        // 4️⃣ Everything else
                        .anyRequest().authenticated()
                )

                // 🔹 Add JWT filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
