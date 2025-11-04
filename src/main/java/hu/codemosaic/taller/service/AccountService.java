package hu.codemosaic.taller.service;

import hu.codemosaic.taller.db.AppUserDb;
import hu.codemosaic.taller.dto.AccountContainerDto;
import hu.codemosaic.taller.dto.AccountDto;
import hu.codemosaic.taller.entity.AccountContainerEntity;
import hu.codemosaic.taller.entity.AccountEntity;
import hu.codemosaic.taller.repository.AccountContainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountContainerRepository accountContainerRepository;
    private final AppUserDb appUserDb;

    public List<AccountContainerDto> getAllAccounts() {
        return accountContainerRepository.findAll().stream().map(accountContainerEntity -> AccountContainerDto.builder()
                .name(accountContainerEntity.getName())
                .subaccounts(accountContainerEntity.getAccounts().stream()
                    .map(entity -> AccountDto.builder()
                        .name(entity.getName())
                        .balance(entity.getBalance())
                        .currency(entity.getCurrency())
                        .build()
                    )
                    .toList())
                .build())
            .toList();
    }

    @Transactional
    public AccountContainerDto createAccount(AccountContainerDto accountContainerDto, String currentUsername) {
        AccountContainerEntity entity = new AccountContainerEntity();
        entity.setName(accountContainerDto.getName());
        entity.setOwner(appUserDb.findByUsername(currentUsername));
        entity.setAccounts(mapSubAccountEntitiesFromAccountDto(accountContainerDto, entity));
        var result = accountContainerRepository.save(entity);
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
