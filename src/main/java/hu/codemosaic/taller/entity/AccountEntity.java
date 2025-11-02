package hu.codemosaic.taller.entity;

import hu.codemosaic.taller.enums.AccountType;
import hu.codemosaic.taller.enums.Currency;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "account")
public class AccountEntity extends BaseEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    private Currency currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountContainer_id", nullable = false)
    private AccountContainerEntity accountContainer;

    private BigDecimal balance = BigDecimal.ZERO;

}
