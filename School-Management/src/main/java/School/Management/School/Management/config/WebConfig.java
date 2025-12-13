package School.Management.School.Management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // ⭐ 1. Define the allowed origins explicitly. ⭐
        // Replace 'YOUR-NETLIFY-DOMAIN.netlify.app' with your actual frontend URL.
        String[] allowedOrigins = {
            "https://school-management-system-vhn2.onrender.com", // Your Render URL itself (for safety/same-origin)
            "https://school-management-s.netlify.app",         // ⭐ PRIMARY FRONTEND URL ⭐
            "http://localhost:8080",                            // Local Spring Boot testing
            "http://localhost:3000"                             // Common local frontend port (if you use React/Vue)
        };
        
        registry.addMapping("/api/**") // Apply CORS rules to all /api endpoints
                .allowedOrigins(allowedOrigins)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true); // Required since you are dealing with JWTs/credentials
    }
}
