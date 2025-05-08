package com.example.dto.frontal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Запрос на создание кредитной заявки.
 */
@Data
public class CreditApplicationRequest {

    /**
     * Cумма кредита.
     */
    @DecimalMin(value = "0.0")
    @Digits(integer=6, fraction=2)
    private BigDecimal creditAmount;

    /**
     * Месячный доход заёмщика.
     */
    @DecimalMin(value = "0.0")
    @Digits(integer=6, fraction=2)
    private BigDecimal borrowersMonthlyIncome;

    /**
     * Кредитная нагрузка заёмщика (в процентах).
     */
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.00")
    @Digits(integer=3, fraction=2)
    private BigDecimal borrowersCreditLoad;

    /**
     * Кредитный рейтинг заёмщика.
     */
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.00")
    @Digits(integer=3, fraction=2)
    private BigDecimal borrowersCreditRate;

    /**
     * Срок кредита (в месяцах)
     */
    @Positive
    private Integer creditTermInMonths;
}
