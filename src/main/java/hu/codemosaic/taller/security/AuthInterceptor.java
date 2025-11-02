package hu.codemosaic.taller.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // If the handler isn't a controller method, let it pass (e.g., static resources)
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Class<?> beanType = handlerMethod.getBeanType(); // The controller class

        // 1. Check for @NoAuth on the method
        if (method.isAnnotationPresent(NoAuth.class)) {
            return true; // Access granted
        }

        // 2. Check for @Auth on the method
        if (method.isAnnotationPresent(Auth.class)) {
            return checkAuthentication(request, response); // Check auth and return result
        }

        // 3. Check for @NoAuth on the class
        if (beanType.isAnnotationPresent(NoAuth.class)) {
            return true; // Access granted
        }

        // 4. Check for @Auth on the class
        if (beanType.isAnnotationPresent(Auth.class)) {
            return checkAuthentication(request, response); // Check auth and return result
        }

        // 5. Default: No auth annotation found, allow access
        return true;
    }

    /**
     * This is your actual authentication logic.
     * Check for a session, a JWT token in the "Authorization" header, etc.
     */
    private boolean checkAuthentication(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // --- This is placeholder logic. Replace it with your real auth check. ---

        String token = request.getHeader("Authorization");

        // Example: Check if a valid "Bearer token" exists
        if (token != null && token.startsWith("Bearer ")) {
            String rawToken = token.substring(7);
            if (jwtService.isValidToken(rawToken)) {
                String username = jwtService.extractUsername(rawToken);
                UserContext.setUsername(username); // ✅ store in context
                return true;
            }
        }

        // --- End of placeholder logic ---

        // If authentication fails:
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
        response.getWriter().write("Unauthorized: You must be logged in to access this resource.");
        return false; // Access denied
    }

    private boolean isValidToken(String token) {
        return jwtService.isValidToken(token);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContext.clear(); // ✅ cleanup
    }
}