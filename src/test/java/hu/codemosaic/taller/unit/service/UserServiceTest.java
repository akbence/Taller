package hu.codemosaic.taller.unit.service;

import hu.codemosaic.taller.dto.UserDto;
import hu.codemosaic.taller.entity.AppUserEntity;
import hu.codemosaic.taller.repository.AppUserRepository;
import hu.codemosaic.taller.security.JwtService;
import hu.codemosaic.taller.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private AppUserRepository appUserRepository;
    private UserService userService;
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        appUserRepository = mock(AppUserRepository.class);
        userService = new UserService(appUserRepository, jwtService);
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
}