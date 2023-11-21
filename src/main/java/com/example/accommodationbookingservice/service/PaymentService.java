package com.example.accommodationbookingservice.service;

import com.example.accommodationbookingservice.dto.payment.PaymentResponseDto;
import java.util.List;

public interface PaymentService {
    void successPayment(String sessionId);

    void cancelPayment(String sessionId);

    List<PaymentResponseDto> getPaymentsByUserId(Long userId);
}
