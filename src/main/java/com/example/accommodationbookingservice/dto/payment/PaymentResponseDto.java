package com.example.accommodationbookingservice.dto.payment;

import com.example.accommodationbookingservice.model.Payment;
import java.math.BigDecimal;

public record PaymentResponseDto(
        Long id,
        Payment.Status status,
        Long bookingId,
        BigDecimal amount
) {
}
