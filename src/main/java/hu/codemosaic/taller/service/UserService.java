package hu.codemosaic.taller.service;

import hu.codemosaic.taller.dto.UserDto;
import hu.codemosaic.taller.entity.AppUserEntity;
import hu.codemosaic.taller.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AppUserRepository appUserRepository;

    public List<UserDto> getAllUsers() {
        return appUserRepository.findAll()
                .stream().map(AppUserEntity::getUsername)
                .map((username) -> UserDto.builder().username(username).build())
                .toList();
    }
}
