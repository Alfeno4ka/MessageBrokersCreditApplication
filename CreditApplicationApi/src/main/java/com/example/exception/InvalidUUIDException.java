package com.example.exception;

import com.example.constants.ErrorLogMessages;
import lombok.Getter;

/**
 * Исключение при невалидном UUID.
 */
@Getter
public class InvalidUUIDException extends RuntimeException {

    private final String value;

    public InvalidUUIDException(String value) {
        super(ErrorLogMessages.INVALID_UUID + value);
        this.value = value;
    }
}
