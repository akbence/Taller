package hu.codemosaic.taller.entity;

import hu.codemosaic.taller.enums.Currency;
import hu.codemosaic.taller.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "accountTransaction")
@Data
public class AccountTransactionEntity extends BaseEntity {

    private String description;
    private BigDecimal amount;
    private double latitude;
    private double longitude;
    private Instant transactionTime;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_account_id")
    private AccountEntity targetAccount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUserEntity owner;
}
