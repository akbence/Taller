package hu.codemosaic.taller.service;

import hu.codemosaic.taller.dto.UserDto;
import hu.codemosaic.taller.entity.AppUserEntity;
import hu.codemosaic.taller.repository.AppUserRepository;
import hu.codemosaic.taller.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AppUserRepository appUserRepository;
    private final JwtService jwtService;

    public List<UserDto> getAllUsers() {
        return appUserRepository.findAll()
                .stream().map(AppUserEntity::getUsername)
                .map((username) -> UserDto.builder().username(username).build())
                .toList();
    }

    public UserDto createUser(UserDto userDto) {
        AppUserEntity appUserEntity = new AppUserEntity();
        appUserEntity.setUsername(userDto.getUsername());
        AppUserEntity savedEntity = appUserRepository.save(appUserEntity);
        return UserDto.builder()
                .username(savedEntity.getUsername())
                .build();
    }

    public String login(String username, String password) {
        //Todo: implement real authentication for password
        var user = appUserRepository.findByUsername(username);
        return user.map(appUserEntity -> jwtService.generateToken(appUserEntity.getUsername())).orElse(null);
    }
}
