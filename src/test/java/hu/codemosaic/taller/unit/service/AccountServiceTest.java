package hu.codemosaic.taller.unit.service;

import hu.codemosaic.taller.db.AppUserDb;
import hu.codemosaic.taller.dto.AccountContainerDto;
import hu.codemosaic.taller.dto.AccountDto;
import hu.codemosaic.taller.entity.AccountContainerEntity;
import hu.codemosaic.taller.entity.AccountEntity;
import hu.codemosaic.taller.enums.Currency;
import hu.codemosaic.taller.repository.AccountContainerRepository;
import hu.codemosaic.taller.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    private AccountContainerRepository accountContainerRepository;
    private AccountService accountService;
    private AppUserDb appUserDb;

    @BeforeEach
    void setup() {
        accountContainerRepository = mock(AccountContainerRepository.class);
        appUserDb = mock(AppUserDb.class);
        accountService = new AccountService(accountContainerRepository, appUserDb);
    }

    @Test
    void testGetAllAccounts_returnsMappedDtos() {
        // Arrange
        AccountEntity sub1 = new AccountEntity();
        sub1.setName("Savings");
        sub1.setBalance(BigDecimal.valueOf(1000));
        sub1.setCurrency(Currency.USD);

        AccountContainerEntity accountContainerEntity = new AccountContainerEntity();
        accountContainerEntity.setName("Main Account");
        accountContainerEntity.setAccounts(List.of(sub1));
        var appUserEntity = new hu.codemosaic.taller.entity.AppUserEntity();
        appUserEntity.setUsername("john_doe");
        accountContainerEntity.setOwner(appUserEntity);

        when(accountContainerRepository.findAll()).thenReturn(List.of(accountContainerEntity));

        // Act
        List<AccountContainerDto> result = accountService.getAllAccounts();

        // Assert
        assertEquals(1, result.size());
        AccountContainerDto dto = result.getFirst();
        assertEquals("Main Account", dto.getName());
        assertEquals(1, dto.getSubaccounts().size());
        assertEquals("Savings", dto.getSubaccounts().getFirst().getName());
    }

    @Test
    void testCreateAccount_savesAndReturnsMappedDto() {
        // Arrange
        AccountDto subDto = AccountDto.builder()
                .name("Checking")
                .balance(BigDecimal.valueOf(500))
                .currency(Currency.EUR)
                .build();

        AccountContainerDto inputDto = AccountContainerDto.builder()
                .name("New Account")
                .subaccounts(List.of(subDto))
                .build();

        AccountEntity subEntity = new AccountEntity();
        subEntity.setName("Checking");
        subEntity.setBalance(BigDecimal.valueOf(500));
        subEntity.setCurrency(Currency.EUR);

        AccountContainerEntity savedEntity = new AccountContainerEntity();
        savedEntity.setName("New Account");
        savedEntity.setAccounts(List.of(subEntity));
        var appUserEntity = new hu.codemosaic.taller.entity.AppUserEntity();
        appUserEntity.setUsername("jane_doe");
        savedEntity.setOwner(appUserEntity); // assuming constructor or setter

        when(accountContainerRepository.save(any(AccountContainerEntity.class))).thenReturn(savedEntity);
        when(appUserDb.findByUsername("jane_doe")).thenReturn(appUserEntity);

        // Act
        AccountContainerDto result = accountService.createAccount(inputDto, "jane_doe");

        // Assert
        assertEquals("New Account", result.getName());
        assertEquals(1, result.getSubaccounts().size());
        assertEquals("Checking", result.getSubaccounts().getFirst().getName());
    }
}
