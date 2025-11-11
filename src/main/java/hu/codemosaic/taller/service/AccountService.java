package hu.codemosaic.taller.service;

import hu.codemosaic.taller.db.AccountContainerDb;
import hu.codemosaic.taller.db.AccountDb;
import hu.codemosaic.taller.db.AppUserDb;
import hu.codemosaic.taller.dto.AccountContainerDto;
import hu.codemosaic.taller.dto.AccountDto;
import hu.codemosaic.taller.entity.AccountContainerEntity;
import hu.codemosaic.taller.entity.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountContainerDb accountContainerDb;
    private final AccountDb accountDb;
    private final AppUserDb appUserDb;

    public List<AccountContainerDto> getAllAccountsByOwnerId(UUID currentUserId) {
        return accountContainerDb.findAllByOwnerId(currentUserId).stream().map(accountContainerEntity -> AccountContainerDto.builder()
                .name(accountContainerEntity.getName())
                .id(accountContainerEntity.getId())
                .subaccounts(accountContainerEntity.getAccounts().stream()
                    .map(entity -> AccountDto.builder()
                        .id(entity.getId())
                        .name(entity.getName())
                        .balance(entity.getBalance())
                        .currency(entity.getCurrency())
                        .accountContainer(entity.getAccountContainer().getId())
                        .build()
                    )
                    .toList())
                .build())
            .toList();
    }

    @Transactional
    public AccountContainerDto createAccountContainer(AccountContainerDto accountContainerDto, String currentUsername) {
        AccountContainerEntity entity = new AccountContainerEntity();
        entity.setName(accountContainerDto.getName());
        entity.setOwner(appUserDb.findByUsername(currentUsername));
        entity.setAccounts(mapSubAccountEntitiesFromAccountDto(accountContainerDto, entity));
        var result = accountContainerDb.save(entity);
        return AccountContainerDto.builder()
                .name(result.getName())
                .subaccounts(result.getAccounts().stream()
                    .map(subEntity -> AccountDto.builder()
                        .name(subEntity.getName())
                        .balance(subEntity.getBalance())
                        .currency(subEntity.getCurrency())
                        .accountType(subEntity.getAccountType())
                        .build()
                    )
                    .toList())
                .build();
    }

    public List<AccountDto> getAccountsByContainerId(UUID containerId, UUID currentUserId) {
        return accountDb.findByContainerIdAndOwnerId(containerId, currentUserId).stream()
                .map(accountEntity -> AccountDto.builder()
                        .accountType(accountEntity.getAccountType())
                        .id(accountEntity.getId())
                        .name(accountEntity.getName())
                        .balance(accountEntity.getBalance())
                        .currency(accountEntity.getCurrency())
                        .accountContainer(accountEntity.getAccountContainer().getId())
                        .build())
                .toList();
    }


    private List<AccountEntity> mapSubAccountEntitiesFromAccountDto(AccountContainerDto accountContainerDto, AccountContainerEntity accountContainerEntity) {
        return accountContainerDto.getSubaccounts().stream().map(accountDto -> {
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setName(accountDto.getName());
            accountEntity.setBalance(accountDto.getBalance());
            accountEntity.setCurrency(accountDto.getCurrency());
            accountEntity.setAccountType(accountDto.getAccountType());
            accountEntity.setAccountContainer(accountContainerEntity);
            return accountEntity;
        }).toList();
    }
}
