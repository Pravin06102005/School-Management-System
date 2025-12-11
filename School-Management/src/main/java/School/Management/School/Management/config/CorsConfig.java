// package School.Management.School.Management.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration
// public class CorsConfig {

//     @Bean
//     public WebMvcConfigurer corsConfigurer() {
//         return new WebMvcConfigurer() {
//             @Override
//             public void addCorsMappings(CorsRegistry registry) {

//                 registry.addMapping("/**")
//                     .allowedOrigins(
//                             "https://school-management-s.netlify.app",
//                             "https://fit-gym1.netlify.app",
//                             "http://localhost:3000",
//                             "http://localhost:5173"
//                         )
//                         .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
//                         .allowedHeaders("*")
//                         .allowCredentials(true);
//             }
//         };
//     }
// }
