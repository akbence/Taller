package hu.codemosaic.taller.entity;

import hu.codemosaic.taller.enums.AccountType;
import hu.codemosaic.taller.enums.Currency;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "account")
public class AccountEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @ManyToOne(fetch = LAZY)
    private AppUserEntity owner;

    @OneToMany(mappedBy = "account", fetch = LAZY)
    private List<SubAccountEntity> subaccounts = new ArrayList<>();

}
