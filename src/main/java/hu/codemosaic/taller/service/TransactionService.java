package hu.codemosaic.taller.service;

import hu.codemosaic.taller.dto.AccountTransactionDto;
import hu.codemosaic.taller.entity.AccountTransactionEntity;
import hu.codemosaic.taller.exception.CategoryNotFoundException;
import hu.codemosaic.taller.repository.AccountTransactionRepository;
import hu.codemosaic.taller.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountTransactionRepository accountTransactionRepository;
    private final CategoryRepository categoryRepository;

    public AccountTransactionDto createTransaction(AccountTransactionDto accountTransactionDto, UUID currentUserId) {
        AccountTransactionEntity entity = new AccountTransactionEntity();
        entity.setAmount(accountTransactionDto.getAmount());
        entity.setTransactionTime(accountTransactionDto.getTransactionTime());
        entity.setDescription(accountTransactionDto.getDescription());
        entity.setLatitude(accountTransactionDto.getLatitude());
        entity.setLongitude(accountTransactionDto.getLongitude());
        entity.setTransactionType(accountTransactionDto.getTransactionType());
        var categoryEntity = categoryRepository.findByIdAndOwnerId(accountTransactionDto.getCategory().getId(),currentUserId)
            .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        entity.setCategory(categoryEntity);

        var result = accountTransactionRepository.save(entity);
        return AccountTransactionDto.builder()
                .amount(result.getAmount())
                .transactionTime(result.getTransactionTime())
                .description(result.getDescription())
                .build();
    }

    public List<AccountTransactionDto> getAllTransactions() {
        return accountTransactionRepository.findAll().stream().map(transactionEntity -> AccountTransactionDto.builder()
                .amount(transactionEntity.getAmount())
                .transactionTime(transactionEntity.getTransactionTime())
                .description(transactionEntity.getDescription())
                .build())
            .toList();
    }
}
