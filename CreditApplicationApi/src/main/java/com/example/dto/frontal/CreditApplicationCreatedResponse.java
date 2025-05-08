package com.example.dto.frontal;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.UUID;

/**
 * ДТО результата создания кредитной заявки.
 */
@Builder
@Value
public class CreditApplicationCreatedResponse {

    /**
     * Идентификатор созданной заявки.
     */
    private UUID applicationId;
}
