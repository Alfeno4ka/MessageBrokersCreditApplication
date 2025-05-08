package com.example.dto.frontal;

import com.example.entity.ApplicationStatus;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

/**
 * ДТО статуса кредитной заявки.
 */
@Builder
@Value
public class CreditApplicationStatusResponse {

    UUID applicationId;
    ApplicationStatus status;
}
