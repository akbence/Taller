package hu.codemosaic.taller.dto;

import hu.codemosaic.taller.entity.AccountEntity;
import hu.codemosaic.taller.enums.AccountType;
import hu.codemosaic.taller.enums.Currency;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class SubAccountDto {
    private String name;

    private AccountType accountType;

    private Currency currency;

    private AccountEntity account;

    private BigDecimal balance;
}
