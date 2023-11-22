package com.example.accommodationbookingservice.dto.payment;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePaymentResponseDto {
    private String paymentUrl;
}
