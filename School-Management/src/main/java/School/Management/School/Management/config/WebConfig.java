package School.Management.School.Management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        String[] allowedOrigins = {
            "https://school-management-system-vhn2.onrender.com",
            "https://school-management-s.netlify.app", // Your Netlify domain
            "http://localhost:8080",
            "http://localhost:3000"
        };
        
        registry.addMapping("/api/**") 
                .allowedOrigins(allowedOrigins)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH") 
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
