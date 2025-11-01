package hu.codemosaic.taller.unit.security;

import hu.codemosaic.taller.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setup() {
        // 32+ chars for HMAC-SHA
        String secret = "my-super-secret-key-12345678901234567890123456789012";
        jwtService = new JwtService(secret);
    }

    @Test
    void testGenerateToken_returnsValidJwt() {
        // Arrange
        String username = "testuser";

        // Act
        String token = jwtService.generateToken(username);

        // Assert
        assertNotNull(token);
        assertTrue(jwtService.isValidToken(token));
        assertEquals(username, jwtService.extractUsername(token));
    }

    @Test
    void testIsValidToken_withInvalidToken_returnsFalse() {
        // Arrange
        String invalidToken = "this.is.not.a.valid.token";

        // Act
        boolean result = jwtService.isValidToken(invalidToken);

        // Assert
        assertFalse(result);
    }

    @Test
    void testExtractUsername_returnsCorrectUsername() {
        // Arrange
        String username = "bence";
        String token = jwtService.generateToken(username);

        // Act
        String extracted = jwtService.extractUsername(token);

        // Assert
        assertEquals(username, extracted);
    }
}

