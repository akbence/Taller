package hu.codemosaic.taller.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;


/**
 * EUR-based exchange rates for a given date.
 * Example: if dateOfRate = 2025-10-30,
 *          chf = 0.97, huf = 390.50, usd = 1.08,
 * it means 1 EUR = 0.97 CHF, 390.50 HUF, 1.08 USD.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "exchange_rate")
public class EurBasedExchangeRateEntity extends BaseEntity {

    @Column(nullable = false)
    private LocalDate dateOfRate;

    @Column(nullable = false)
    private BigDecimal chf;

    @Column(nullable = false)
    private BigDecimal huf;

    @Column(nullable = false)
    private BigDecimal usd;
}
