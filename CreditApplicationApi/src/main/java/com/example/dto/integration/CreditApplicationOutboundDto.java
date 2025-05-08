package com.example.dto.integration;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Модель данных "Заявка на получение кредита" для отправки в сервис принятия решений.
 */
@Data
public class CreditApplicationOutboundDto {
    private UUID id;
    private BigDecimal creditAmount;
    private BigDecimal borrowersMonthlyIncome;
    private BigDecimal borrowersCreditLoad;
    private BigDecimal borrowersCreditRate;
    private Integer creditTermInMonths;
}
