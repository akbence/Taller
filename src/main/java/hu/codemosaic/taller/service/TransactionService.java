package hu.codemosaic.taller.service;

import hu.codemosaic.taller.dto.AccountDto;
import hu.codemosaic.taller.dto.AccountTransactionDto;
import hu.codemosaic.taller.entity.AccountEntity;
import hu.codemosaic.taller.entity.AccountTransactionEntity;
import hu.codemosaic.taller.repository.AccountTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountTransactionRepository accountTransactionRepository;;

    public AccountTransactionDto createTransaction(AccountTransactionDto accountTransactionDto) {
        AccountTransactionEntity entity = new AccountTransactionEntity();
        entity.setAmount(accountTransactionDto.getAmount());
        entity.setTransactionTime(accountTransactionDto.getTransactionTime());
        entity.setDescription(accountTransactionDto.getDescription());
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
