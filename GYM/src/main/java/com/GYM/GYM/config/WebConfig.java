package com.GYM.GYM.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry; // 👈 NEW IMPORT
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 1. Static Resource Mapping
        // Maps URL path /uploads/** to the absolute path of the 'uploads' folder
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/uploads/")
                // Important: Add resource chain for correct handling
                .resourceChain(true);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) { // 👈 NEW METHOD
        // 2. Global CORS Configuration
        // Allows all origins (*) to access all paths (/**) with standard HTTP methods.
        // This is necessary because your HTML page is on a different origin/port than your backend server.
        registry.addMapping("/**")
                .allowedOrigins("*") // Be more restrictive in production (e.g., "http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*");
    }
}