package hu.codemosaic.taller.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class AccountTransactionEntity extends BaseEntity{

    private String description;
    private BigDecimal amount;
    private double latitude;
    private double longitude;
    private Instant transactionTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUserEntity appUserEntity;
}
