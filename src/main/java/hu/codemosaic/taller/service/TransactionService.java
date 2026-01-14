package hu.codemosaic.taller.service;

import hu.codemosaic.taller.db.AccountTransactionDb;
import hu.codemosaic.taller.db.AppUserDb;
import hu.codemosaic.taller.db.CategoryDb;
import hu.codemosaic.taller.dto.AccountTransactionDto;
import hu.codemosaic.taller.entity.AccountTransactionEntity;
import hu.codemosaic.taller.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static hu.codemosaic.taller.util.MapperUtil.*;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountTransactionDb accountTransactionDb;
    private final CategoryDb categoryDb;
    private final AppUserDb appUserDb;

    @Transactional
    public AccountTransactionDto createTransaction(AccountTransactionDto dto, UUID currentUserId) {
        var entity = buildEntityFromDto(dto, currentUserId);
        var saved = accountTransactionDb.save(entity);
        return mapAccountTransactionEntityToAccountTransactionDto(saved);
    }

    public List<AccountTransactionDto> getTransactions(UUID currentUserId) {
        return accountTransactionDb.findAllByUserId(currentUserId).stream()
                .map(MapperUtil::mapAccountTransactionEntityToAccountTransactionDto)
                .toList();
    }

    @Transactional
    public List<AccountTransactionDto> createBulkTransaction(List<AccountTransactionDto> dtoList, UUID currentUserId) {
        return dtoList.stream()
                .map(dto -> buildEntityFromDto(dto, currentUserId))
                .map(accountTransactionDb::save)
                .map(MapperUtil::mapAccountTransactionEntityToAccountTransactionDto)
                .toList();
    }

    private AccountTransactionEntity buildEntityFromDto(AccountTransactionDto dto, UUID currentUserId) {
        var entity = new AccountTransactionEntity();
        entity.setAmount(dto.getAmount());
        entity.setTransactionTime(dto.getTransactionTime());
        entity.setDescription(dto.getDescription());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        entity.setTransactionType(dto.getTransactionType());

        var owner = appUserDb.findById(currentUserId);
        entity.setOwner(owner);

        var category = categoryDb.findByIdAndOwnerId(dto.getCategory().getId(), currentUserId);
        entity.setCategory(category);

        return entity;
    }
}
