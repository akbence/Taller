package hu.codemosaic.taller.config;

import hu.codemosaic.taller.security.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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
}