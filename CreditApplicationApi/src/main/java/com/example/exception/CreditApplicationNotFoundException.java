package com.example.exception;

import com.example.constants.ErrorLogMessages;
import lombok.Getter;

import java.util.UUID;

/**
 * Исключение когда пользователь не найден по айди.
 */
@Getter
public class CreditApplicationNotFoundException extends RuntimeException {

    private UUID applicationId;

    public CreditApplicationNotFoundException(UUID applicationId) {
        super(ErrorLogMessages.CREDIT_APPLICATION_NOT_FOUND + applicationId);
        this.applicationId = applicationId;
    }
}
