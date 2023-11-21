package com.example.accommodationbookingservice.service;
import java.math.BigDecimal;

public interface PaymentService {
    String createPaymentIntent(Long bookingId, BigDecimal amount);

}