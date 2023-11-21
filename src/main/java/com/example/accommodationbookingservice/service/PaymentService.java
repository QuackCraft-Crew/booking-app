package com.example.accommodationbookingservice.service;

import com.example.accommodationbookingservice.dto.payment.PaymentDto;

import java.util.List;

public interface PaymentService {
    List<PaymentDto> getPaymentsByUserId(Long userId);
}
