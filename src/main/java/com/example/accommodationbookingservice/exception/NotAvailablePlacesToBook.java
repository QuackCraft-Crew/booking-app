package com.example.accommodationbookingservice.exception;

public class NotAvailablePlacesToBook extends RuntimeException {
    public NotAvailablePlacesToBook(String message) {
        super(message);
    }

    public NotAvailablePlacesToBook(String message, Throwable cause) {
        super(message, cause);
    }
}
