package hu.codemosaic.taller.db;

import hu.codemosaic.taller.entity.AccountContainerEntity;
import hu.codemosaic.taller.repository.AccountContainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountContainerDb {
    private final AccountContainerRepository repository;


    public AccountContainerEntity save(AccountContainerEntity user) {
        return repository.save(user);
    }

    public List<AccountContainerEntity> findAllByOwnerId(UUID ownerId) {
        return repository.findAllByOwnerId(ownerId);
    }

    public Optional<AccountContainerEntity> findByOwnerIdAndContainerId(UUID currentUserId, UUID containerId) {
        return null;
    }
}
