package hu.codemosaic.taller.unit.security;

import hu.codemosaic.taller.entity.AppUserEntity;
import hu.codemosaic.taller.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

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
        UUID userId = UUID.randomUUID();
        AppUserEntity appUserEntity = new AppUserEntity();
        appUserEntity.setUsername(username);
        appUserEntity.setId(userId);

        // Act
        String token = jwtService.generateToken(appUserEntity);

        // Assert
        assertNotNull(token);
        assertTrue(jwtService.isValidToken(token));
        assertEquals(username, jwtService.extractUserContext(token).get("username"));
        assertEquals(userId.toString(), jwtService.extractUserContext(token).get("userId"));
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
    void testExtractUsername_returnsCorrectUserContext() {
        // Arrange
        String username = "testuser";
        UUID userId = UUID.randomUUID();
        AppUserEntity appUserEntity = new AppUserEntity();
        appUserEntity.setUsername(username);
        appUserEntity.setId(userId);
        String token = jwtService.generateToken(appUserEntity);

        // Act
        Map<String,String> extracted = jwtService.extractUserContext(token);

        // Assert
        assertEquals(username, extracted.get("username"));
        assertEquals(userId.toString(), extracted.get("userId"));
    }
}

