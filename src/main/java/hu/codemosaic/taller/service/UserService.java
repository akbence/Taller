package hu.codemosaic.taller.service;

import hu.codemosaic.taller.db.AppUserDb;
import hu.codemosaic.taller.dto.UserDto;
import hu.codemosaic.taller.entity.AppUserEntity;
import hu.codemosaic.taller.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AppUserDb appUserDb;
    private final JwtService jwtService;

    public List<UserDto> getAllUsers() {
        return appUserDb.findAll()
                .stream().map(AppUserEntity::getUsername)
                .map((username) -> UserDto.builder().username(username).build())
                .toList();
    }

    public UserDto createUser(UserDto userDto) {
        AppUserEntity appUserEntity = new AppUserEntity();
        appUserEntity.setUsername(userDto.getUsername());
        AppUserEntity savedEntity = appUserDb.save(appUserEntity);
        return UserDto.builder()
                .username(savedEntity.getUsername())
                .build();
    }

    public String login(String username, String password) {
        //Todo: implement real authentication for password
        var user = appUserDb.findByUsername(username);
        return jwtService.generateToken(user.getUsername());
    }
}
