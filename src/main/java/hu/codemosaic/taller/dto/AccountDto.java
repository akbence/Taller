package hu.codemosaic.taller.dto;

import hu.codemosaic.taller.entity.AccountContainerEntity;
import hu.codemosaic.taller.enums.AccountType;
import hu.codemosaic.taller.enums.Currency;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class AccountDto {
    private String name;

    private AccountType accountType;

    private Currency currency;

    private AccountContainerEntity account;

    private BigDecimal balance;
}
