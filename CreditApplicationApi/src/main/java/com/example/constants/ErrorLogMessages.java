package com.example.constants;

import lombok.experimental.UtilityClass;

/**
 * Сообщения об ошибках.
 */
@UtilityClass
public class ErrorLogMessages {

    public static final String COMMON_ERROR = "E-01: Unexpected error: ";
    public static final String INVALID_UUID = "E-02: Invalid UUID value: ";
    public static final String CREDIT_APPLICATION_NOT_FOUND = "E-03: Credit application not found by id: ";
}
