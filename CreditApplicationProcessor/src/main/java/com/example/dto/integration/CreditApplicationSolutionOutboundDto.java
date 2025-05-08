package com.example.dto.integration;

import lombok.*;

import java.util.UUID;

/**
 * Модель данных с решением по кредитной заявке.
 */
@Data
public class CreditApplicationSolutionOutboundDto {
    private UUID applicationId;
    private Solution applicationSolution;
}
