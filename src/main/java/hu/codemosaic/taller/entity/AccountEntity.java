package hu.codemosaic.taller.entity;

import hu.codemosaic.taller.enums.AccountType;
import hu.codemosaic.taller.enums.Currency;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "account")
public class AccountEntity extends BaseEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountContainer_id", nullable = false)
    private AccountContainerEntity accountContainer;

    @OneToMany(mappedBy = "account", fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountTransactionEntity> transactions = new ArrayList<>();

    private BigDecimal balance = BigDecimal.ZERO;

}
