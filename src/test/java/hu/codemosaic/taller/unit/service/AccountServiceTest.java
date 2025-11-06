package hu.codemosaic.taller.unit.service;

import hu.codemosaic.taller.db.AccountContainerDb;
import hu.codemosaic.taller.db.AccountDb;
import hu.codemosaic.taller.db.AppUserDb;
import hu.codemosaic.taller.dto.AccountContainerDto;
import hu.codemosaic.taller.dto.AccountDto;
import hu.codemosaic.taller.entity.AccountContainerEntity;
import hu.codemosaic.taller.entity.AccountEntity;
import hu.codemosaic.taller.entity.AppUserEntity;
import hu.codemosaic.taller.enums.AccountType;
import hu.codemosaic.taller.enums.Currency;
import hu.codemosaic.taller.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    private AccountContainerDb accountContainerDb;
    private AccountService accountService;
    private AppUserDb appUserDb;
    private final AccountDb accountDb = mock(AccountDb.class);

    @BeforeEach
    void setup() {
        accountContainerDb = mock(AccountContainerDb.class);
        appUserDb = mock(AppUserDb.class);
        accountService = new AccountService(accountContainerDb, accountDb, appUserDb);
    }

    @Test
    void testGetAllAccountsByOwnerId_returnsMappedDtos() {
        // Arrange
        UUID userId = UUID.randomUUID();

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setName("Savings");
        accountEntity.setBalance(BigDecimal.valueOf(1000));
        accountEntity.setCurrency(Currency.USD);

        AccountContainerEntity containerEntity = new AccountContainerEntity();
        containerEntity.setName("Main Account");
        containerEntity.setAccounts(List.of(accountEntity));

        AppUserEntity appUserEntity = new AppUserEntity();
        appUserEntity.setId(userId);
        appUserEntity.setUsername("john_doe");
        containerEntity.setOwner(appUserEntity);

        when(accountContainerDb.findAllByOwnerId(userId)).thenReturn(List.of(containerEntity));

        // Act
        List<AccountContainerDto> result = accountService.getAllAccountsByOwnerId(userId);

        // Assert
        assertEquals(1, result.size());
        AccountContainerDto dto = result.getFirst();
        assertEquals("Main Account", dto.getName());
        assertEquals(1, dto.getSubaccounts().size());
        AccountDto subDto = dto.getSubaccounts().getFirst();
        assertEquals("Savings", subDto.getName());
        assertEquals(BigDecimal.valueOf(1000), subDto.getBalance());
        assertEquals(Currency.USD, subDto.getCurrency());
    }

    @Test
    void testCreateAccount_Container_savesAndReturnsMappedDto() {
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

        when(accountContainerDb.save(any(AccountContainerEntity.class))).thenReturn(savedEntity);
        when(appUserDb.findByUsername("jane_doe")).thenReturn(appUserEntity);

        // Act
        AccountContainerDto result = accountService.createAccountContainer(inputDto, "jane_doe");

        // Assert
        assertEquals("New Account", result.getName());
        assertEquals(1, result.getSubaccounts().size());
        assertEquals("Checking", result.getSubaccounts().getFirst().getName());
    }

    @Test
    void getAccountsByContainerId_returnsMappedDtos() {
        // Arrange
        UUID containerId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setName("Savings");
        accountEntity.setBalance(BigDecimal.valueOf(1000));
        accountEntity.setCurrency(Currency.USD);
        accountEntity.setAccountType(AccountType.CHECKING);

        when(accountDb.findByContainerIdAndOwnerId(containerId, userId))
                .thenReturn(List.of(accountEntity));

        // Act
        List<AccountDto> result = accountService.getAccountsByContainerId(containerId, userId);

        // Assert
        assertEquals(1, result.size());
        AccountDto dto = result.getFirst();
        assertEquals("Savings", dto.getName());
        assertEquals(BigDecimal.valueOf(1000), dto.getBalance());
        assertEquals(Currency.USD, dto.getCurrency());
        assertEquals(AccountType.CHECKING, dto.getAccountType());
    }
}
