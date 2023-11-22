package com.example.accommodationbookingservice.exception;

public class CustomTelegramApiException extends RuntimeException {
    public CustomTelegramApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
