package hu.codemosaic.taller.db;

import hu.codemosaic.taller.entity.AppUserEntity;
import hu.codemosaic.taller.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserDb {
    private final AppUserRepository repository;

    public AppUserEntity findByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    public AppUserEntity save(AppUserEntity user) {
        return repository.save(user);
    }

    public List<AppUserEntity> findAll() {
        return repository.findAll();
    }
}
