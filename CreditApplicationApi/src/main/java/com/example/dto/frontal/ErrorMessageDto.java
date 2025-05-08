package com.example.dto.frontal;

import lombok.Getter;

/**
 * ДТО сообщения об ошибке.
 */
@Getter
public class ErrorMessageDto {
    String code;
    String message;

    public ErrorMessageDto(String message) {
        this.code = message.substring(0, 4);
        this.message = message.substring(5);
    }
}
