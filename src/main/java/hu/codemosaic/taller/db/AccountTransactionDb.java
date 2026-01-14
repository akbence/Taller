package hu.codemosaic.taller.db;

import hu.codemosaic.taller.entity.AccountTransactionEntity;
import hu.codemosaic.taller.repository.AccountTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountTransactionDb {
    private final AccountTransactionRepository accountTransactionRepository;

    public List<AccountTransactionEntity> findAllByUserId(UUID userId){
        return accountTransactionRepository.findAllById(List.of(userId));
    }

    public AccountTransactionEntity save(AccountTransactionEntity entity) {
        return accountTransactionRepository.save(entity);
    }

    public List<AccountTransactionEntity> saveAll(List<AccountTransactionEntity> entities) {
        return accountTransactionRepository.saveAll(entities);
    }
}
