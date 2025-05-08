package com.example.dto.integration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Модель данных "Заявка на получение кредита" для расчета решения по кредиту.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditApplicationInboundDto {
    private UUID id;
    private BigDecimal creditAmount;
    private BigDecimal borrowersMonthlyIncome;
    private BigDecimal borrowersCreditLoad;
    private BigDecimal borrowersCreditRate;
    private Integer creditTermInMonths;
}
