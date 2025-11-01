package hu.codemosaic.taller.unit.service;

import hu.codemosaic.taller.dto.AccountDto;
import hu.codemosaic.taller.dto.SubAccountDto;
import hu.codemosaic.taller.dto.UserDto;
import hu.codemosaic.taller.entity.AccountEntity;
import hu.codemosaic.taller.entity.SubAccountEntity;
import hu.codemosaic.taller.enums.Currency;
import hu.codemosaic.taller.repository.AccountRepository;
import hu.codemosaic.taller.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    private AccountRepository accountRepository;
    private AccountService accountService;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        accountService = new AccountService(accountRepository);
    }

    @Test
    void testGetAllAccounts_returnsMappedDtos() {
        // Arrange
        SubAccountEntity sub1 = new SubAccountEntity();
        sub1.setName("Savings");
        sub1.setBalance(BigDecimal.valueOf(1000));
        sub1.setCurrency(Currency.USD);

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setName("Main Account");
        accountEntity.setSubaccounts(List.of(sub1));
        var appUserEntity = new hu.codemosaic.taller.entity.AppUserEntity();
        appUserEntity.setUsername("john_doe");
        accountEntity.setOwner(appUserEntity);

        when(accountRepository.findAll()).thenReturn(List.of(accountEntity));

        // Act
        List<AccountDto> result = accountService.getAllAccounts();

        // Assert
        assertEquals(1, result.size());
        AccountDto dto = result.getFirst();
        assertEquals("Main Account", dto.getName());
        assertEquals("john_doe", dto.getOwner().getUsername());
        assertEquals(1, dto.getSubaccounts().size());
        assertEquals("Savings", dto.getSubaccounts().getFirst().getName());
    }

    @Test
    void testCreateAccount_savesAndReturnsMappedDto() {
        // Arrange
        SubAccountDto subDto = SubAccountDto.builder()
                .name("Checking")
                .balance(BigDecimal.valueOf(500))
                .currency(Currency.EUR)
                .build();

        AccountDto inputDto = AccountDto.builder()
                .name("New Account")
                .subaccounts(List.of(subDto))
                .owner(UserDto.builder().username("jane_doe").build()) // not used yet
                .build();

        SubAccountEntity subEntity = new SubAccountEntity();
        subEntity.setName("Checking");
        subEntity.setBalance(BigDecimal.valueOf(500));
        subEntity.setCurrency(Currency.EUR);

        AccountEntity savedEntity = new AccountEntity();
        savedEntity.setName("New Account");
        savedEntity.setSubaccounts(List.of(subEntity));
        var appUserEntity = new hu.codemosaic.taller.entity.AppUserEntity();
        appUserEntity.setUsername("jane_doe");
        savedEntity.setOwner(appUserEntity); // assuming constructor or setter

        when(accountRepository.save(any(AccountEntity.class))).thenReturn(savedEntity);

        // Act
        AccountDto result = accountService.createAccount(inputDto);

        // Assert
        assertEquals("New Account", result.getName());
        assertEquals("jane_doe", result.getOwner().getUsername());
        assertEquals(1, result.getSubaccounts().size());
        assertEquals("Checking", result.getSubaccounts().getFirst().getName());
    }
}
