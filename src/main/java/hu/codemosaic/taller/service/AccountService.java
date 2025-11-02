package hu.codemosaic.taller.service;

import hu.codemosaic.taller.db.AppUserDb;
import hu.codemosaic.taller.dto.AccountDto;
import hu.codemosaic.taller.dto.SubAccountDto;
import hu.codemosaic.taller.entity.AccountEntity;
import hu.codemosaic.taller.entity.SubAccountEntity;
import hu.codemosaic.taller.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AppUserDb appUserDb;

    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll().stream().map(accountEntity -> AccountDto.builder()
                .name(accountEntity.getName())
                .subaccounts(accountEntity.getSubaccounts().stream()
                    .map(entity -> SubAccountDto.builder()
                        .name(entity.getName())
                        .balance(entity.getBalance())
                        .currency(entity.getCurrency())
                        .build()
                    )
                    .toList())
                .build())
            .toList();
    }

    public AccountDto createAccount(AccountDto accountDto, String currentUsername) {
        AccountEntity entity = new AccountEntity();
        entity.setName(accountDto.getName());
        entity.setOwner(appUserDb.findByUsername(currentUsername));
        entity.setSubaccounts(mapSubAccountEntitiesFromAccountDto(accountDto));
        var result = accountRepository.save(entity);
        return AccountDto.builder()
                .name(result.getName())
                .subaccounts(result.getSubaccounts().stream()
                    .map(subEntity -> SubAccountDto.builder()
                        .name(subEntity.getName())
                        .balance(subEntity.getBalance())
                        .currency(subEntity.getCurrency())
                        .build()
                    )
                    .toList())
                .build();
    }

    private List<SubAccountEntity> mapSubAccountEntitiesFromAccountDto(AccountDto accountDto) {
        return accountDto.getSubaccounts().stream().map(subAccountDto -> {
            SubAccountEntity subAccountEntity = new SubAccountEntity();
            subAccountEntity.setName(subAccountDto.getName());
            subAccountEntity.setBalance(subAccountDto.getBalance());
            subAccountEntity.setCurrency(subAccountDto.getCurrency());
            return subAccountEntity;
        }).toList();
    }
}
