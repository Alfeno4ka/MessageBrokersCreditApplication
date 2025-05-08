package com.example.controller;

import com.example.constants.ErrorLogMessages;
import com.example.dto.frontal.ErrorMessageDto;
import com.example.exception.CreditApplicationNotFoundException;
import com.example.exception.InvalidUUIDException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Обработчик исключений.
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorMessageDto> handleUnexpectedException(Exception ex) {
        return new ResponseEntity<>(new ErrorMessageDto(ErrorLogMessages.COMMON_ERROR + ex),
                HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @ExceptionHandler({InvalidUUIDException.class})
    public ResponseEntity<ErrorMessageDto> handleInvalidUUIDException(InvalidUUIDException ex) {
        return new ResponseEntity<>(new ErrorMessageDto(ErrorLogMessages.INVALID_UUID + ex.getValue()),
                HttpStatusCode.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()));
    }

    @ExceptionHandler({CreditApplicationNotFoundException.class})
    public ResponseEntity<ErrorMessageDto> handleInvalidUUIDException(CreditApplicationNotFoundException ex) {
        return new ResponseEntity<>(new ErrorMessageDto(ErrorLogMessages.CREDIT_APPLICATION_NOT_FOUND + ex.getApplicationId()),
                HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
    }
}
