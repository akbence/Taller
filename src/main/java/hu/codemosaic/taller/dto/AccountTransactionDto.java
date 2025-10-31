package hu.codemosaic.taller.dto;

import hu.codemosaic.taller.entity.AppUserEntity;
import hu.codemosaic.taller.entity.BaseEntity;
import hu.codemosaic.taller.entity.CategoryEntity;
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

    private CategoryEntity category;
    private AppUserEntity appUserEntity;
}
