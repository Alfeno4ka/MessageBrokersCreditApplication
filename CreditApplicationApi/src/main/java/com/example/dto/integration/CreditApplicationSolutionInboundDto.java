package com.example.dto.integration;

import com.example.entity.ApplicationStatus;
import lombok.Data;

import java.util.UUID;

/**
 * Модель данных с решением по кредитной заявке.
 */
@Data
public class CreditApplicationSolutionInboundDto {

    private UUID applicationId;
    private ApplicationStatus applicationSolution;
}
