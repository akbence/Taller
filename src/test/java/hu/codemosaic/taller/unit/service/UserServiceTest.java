package hu.codemosaic.taller.unit.service;

import hu.codemosaic.taller.dto.UserDto;
import hu.codemosaic.taller.entity.AppUserEntity;
import hu.codemosaic.taller.repository.AppUserRepository;
import hu.codemosaic.taller.security.JwtService;
import hu.codemosaic.taller.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private AppUserRepository appUserRepository;
    private UserService userService;
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        // Initialize Mocks
        appUserRepository = mock(AppUserRepository.class);
        jwtService = mock(JwtService.class);

        // Initialize Service with Mocks
        userService = new UserService(appUserRepository, jwtService);
    }
    @BeforeEach
    void logTestStart(TestInfo testInfo) {
        System.out.println("Running test: " + testInfo.getDisplayName());
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        AppUserEntity user1 = new AppUserEntity();
        user1.setUsername("alice");
        AppUserEntity user2 = new AppUserEntity();
        user2.setUsername("bob");

        when(appUserRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // Act
        List<UserDto> result = userService.getAllUsers();

        // Assert
        assertEquals(2, result.size());
        assertEquals("alice", result.get(0).getUsername());
        assertEquals("bob", result.get(1).getUsername());
        verify(appUserRepository, times(1)).findAll();
    }

    @Test
    void testCreateUser() {
        // Arrange
        UserDto inputDto = UserDto.builder().username("charlie").build();
        AppUserEntity savedEntity = new AppUserEntity();
        savedEntity.setUsername("charlie");

        when(appUserRepository.save(any(AppUserEntity.class))).thenReturn(savedEntity);

        // Act
        UserDto result = userService.createUser(inputDto);

        // Assert
        assertNotNull(result);
        assertEquals("charlie", result.getUsername());

        ArgumentCaptor<AppUserEntity> captor = ArgumentCaptor.forClass(AppUserEntity.class);
        verify(appUserRepository).save(captor.capture());
        assertEquals("charlie", captor.getValue().getUsername());
    }

    @Test
    void testLogin_success() {
        // Arrange
        String username = "david";
        String password = "testpassword"; // Note: The password isn't actually used due to the TODO in the service
        String expectedToken = "mocked.jwt.token.12345";

        AppUserEntity userEntity = new AppUserEntity();
        userEntity.setUsername(username);

        when(appUserRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(jwtService.generateToken(username)).thenReturn(expectedToken);

        // Act
        String resultToken = userService.login(username, password);

        // Assert
        assertNotNull(resultToken);
        assertEquals(expectedToken, resultToken);
        verify(appUserRepository, times(1)).findByUsername(username);
        verify(jwtService, times(1)).generateToken(username);
    }

    @Test
    void testLogin_userNotFound_returnsNull() {
        // Arrange
        String username = "nonexistent";
        String password = "anypassword";

        when(appUserRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        String resultToken = userService.login(username, password);

        // Assert
        assertNull(resultToken);
        verify(appUserRepository, times(1)).findByUsername(username);
        verify(jwtService, never()).generateToken(anyString()); // Ensure token generation is skipped
    }
}
