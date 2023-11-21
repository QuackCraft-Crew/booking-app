package com.example.accommodationbookingservice.dto.payment;

import java.math.BigDecimal;

public record PaymentDto(
        String nameOfProduct,
        String description,
        Long bookingId,
        BigDecimal amount

) {}
