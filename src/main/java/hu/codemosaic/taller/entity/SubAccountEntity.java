package hu.codemosaic.taller.entity;

import hu.codemosaic.taller.enums.AccountType;
import hu.codemosaic.taller.enums.Currency;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "sub_account")
public class SubAccountEntity extends BaseEntity {

    private String name;

    private AccountType accountType;

    private Currency currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;

    private BigDecimal balance = BigDecimal.ZERO;

}
