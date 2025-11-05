package hu.codemosaic.taller.db;

import hu.codemosaic.taller.entity.AccountEntity;
import hu.codemosaic.taller.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountDb {
    private final AccountRepository accountRepository;

    public List<AccountEntity> findByAccountContainerId(UUID containerId){
        return accountRepository.findByAccountContainerId(containerId);
    }

    public List<AccountEntity> findByContainerIdAndOwnerId(UUID containerId, UUID currentUserId) {
        return accountRepository.findByContainerIdAndOwnerId(containerId, currentUserId);
    }
}
