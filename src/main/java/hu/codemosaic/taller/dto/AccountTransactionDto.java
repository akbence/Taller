package hu.codemosaic.taller.dto;

import hu.codemosaic.taller.entity.BaseEntity;
import hu.codemosaic.taller.enums.TransactionType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class AccountTransactionDto extends BaseEntity {

    private String description;
    private BigDecimal amount;
    private double latitude;
    private double longitude;
    private Instant transactionTime;
    private TransactionType transactionType;

    private AccountDto account;
    private CategoryDto category;
}
