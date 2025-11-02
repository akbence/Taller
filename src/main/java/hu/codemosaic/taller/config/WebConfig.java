package hu.codemosaic.taller.config;

import hu.codemosaic.taller.security.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Apply the interceptor to all routes
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**");

        // You could also exclude paths like /login, /register
        // .excludePathPatterns("/login", "/register");
    }

    /**
     * Configure CORS settings to allow requests from the React frontend.
     * The frontend still experimentally runs on a different port during development.
     * @param registry CorsRegistry to configure CORS mappings
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply to all API paths
                .allowedOrigins("http://localhost:5173") // Allow requests from the React development server
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow necessary HTTP methods
                .allowedHeaders("*") // Allow all headers, including Authorization
                .allowCredentials(true); // Allow cookies or authentication headers (important for JWT)
    }
}