package hu.codemosaic.taller.unit.service;

import hu.codemosaic.taller.db.AccountTransactionDb;
import hu.codemosaic.taller.db.AppUserDb;
import hu.codemosaic.taller.db.CategoryDb;
import hu.codemosaic.taller.dto.AccountTransactionDto;
import hu.codemosaic.taller.dto.CategoryDto;
import hu.codemosaic.taller.entity.AccountTransactionEntity;
import hu.codemosaic.taller.entity.AppUserEntity;
import hu.codemosaic.taller.entity.CategoryEntity;
import hu.codemosaic.taller.enums.TransactionType;
import hu.codemosaic.taller.repository.AccountTransactionRepository;
import hu.codemosaic.taller.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private AccountTransactionRepository accountTransactionRepository;

    @Mock
    private CategoryDb categoryDb;

    @Mock
    private AppUserDb appUserDb;

    @Mock
    private AccountTransactionDb accountTransactionDb;

    @Test
    void createTransaction_shouldMapAndSaveEntity() {
        // Arrange
        UUID userId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        AccountTransactionDto inputDto = AccountTransactionDto.builder()
                .amount(BigDecimal.valueOf(99.99))
                .transactionTime(Instant.now())
                .description("Test Transaction")
                .latitude(47.4979)
                .longitude(19.0402)
                .transactionType(TransactionType.EXPENSE)
                .category(CategoryDto.builder().id(categoryId).build())
                .build();

        AppUserEntity mockUser = new AppUserEntity();
        CategoryEntity mockCategory = new CategoryEntity();

        AccountTransactionEntity savedEntity = new AccountTransactionEntity();
        savedEntity.setAmount(inputDto.getAmount());
        savedEntity.setTransactionTime(inputDto.getTransactionTime());
        savedEntity.setDescription(inputDto.getDescription());

        when(appUserDb.findById(userId)).thenReturn(mockUser);
        when(categoryDb.findByIdAndOwnerId(categoryId, userId)).thenReturn(mockCategory);
        when(accountTransactionRepository.save(any(AccountTransactionEntity.class))).thenReturn(savedEntity);

        // Act
        AccountTransactionDto result = transactionService.createTransaction(inputDto, userId);

        // Assert
        assertEquals(inputDto.getAmount(), result.getAmount());
        assertEquals(inputDto.getTransactionTime(), result.getTransactionTime());
        assertEquals(inputDto.getDescription(), result.getDescription());
    }

    @Test
    void getTransactions_shouldReturnMappedDtos() {
        // Arrange
        AccountTransactionEntity entity = new AccountTransactionEntity();
        entity.setAmount(BigDecimal.valueOf(50));
        entity.setTransactionTime(Instant.now());
        entity.setDescription("Lunch");

        when(accountTransactionDb.findAllByUserId(any())).thenReturn(List.of(entity));

        // Act
        List<AccountTransactionDto> result = transactionService.getTransactions(UUID.randomUUID());

        // Assert
        assertEquals(1, result.size());
        AccountTransactionDto dto = result.getFirst();
        assertEquals(entity.getAmount(), dto.getAmount());
        assertEquals(entity.getTransactionTime(), dto.getTransactionTime());
        assertEquals(entity.getDescription(), dto.getDescription());
    }

}
