package hu.codemosaic.taller.service;

import hu.codemosaic.taller.db.AppUserDb;
import hu.codemosaic.taller.db.CategoryDb;
import hu.codemosaic.taller.dto.AccountTransactionDto;
import hu.codemosaic.taller.entity.AccountTransactionEntity;
import hu.codemosaic.taller.repository.AccountTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static hu.codemosaic.taller.util.MapperUtil.mapCategoryEntityToCategoryDto;
import static hu.codemosaic.taller.util.MapperUtil.mapUserAccountEntityToAccountDto;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountTransactionRepository accountTransactionRepository;
    private final CategoryDb categoryDb;
    private final AppUserDb appUserDb;

    public AccountTransactionDto createTransaction(AccountTransactionDto accountTransactionDto, UUID currentUserId) {
        AccountTransactionEntity entity = new AccountTransactionEntity();
        entity.setAmount(accountTransactionDto.getAmount());
        entity.setTransactionTime(accountTransactionDto.getTransactionTime());
        entity.setDescription(accountTransactionDto.getDescription());
        entity.setLatitude(accountTransactionDto.getLatitude());
        entity.setLongitude(accountTransactionDto.getLongitude());
        entity.setTransactionType(accountTransactionDto.getTransactionType());
        var appUserEntity = appUserDb.findById(currentUserId);
        entity.setOwner(appUserEntity);
        var categoryEntity = categoryDb.findByIdAndOwnerId(accountTransactionDto.getCategory().getId(), currentUserId);
        entity.setCategory(categoryEntity);

        var result = accountTransactionRepository.save(entity);
        return AccountTransactionDto.builder()
                .amount(result.getAmount())
                .transactionTime(result.getTransactionTime())
                .description(result.getDescription())
                .build();
    }

    public List<AccountTransactionDto> getAllTransactions() {
        //Todo: filter by user
        return accountTransactionRepository.findAll().stream().map(transactionEntity -> AccountTransactionDto.builder()
                        .description(transactionEntity.getDescription())
                        .amount(transactionEntity.getAmount())
                        .latitude(transactionEntity.getLatitude())
                        .longitude(transactionEntity.getLongitude())
                        .transactionTime(transactionEntity.getTransactionTime())
                        .transactionType(transactionEntity.getTransactionType())
                        .currency(transactionEntity.getCurrency())
                        .targetAccount(mapUserAccountEntityToAccountDto(transactionEntity.getTargetAccount()))
                        .category(mapCategoryEntityToCategoryDto(transactionEntity.getCategory()))
                .build())
            .toList();
    }
}
