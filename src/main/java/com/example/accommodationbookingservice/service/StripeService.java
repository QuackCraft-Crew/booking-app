package com.example.accommodationbookingservice.service;

import com.example.accommodationbookingservice.dto.payment.CreatePayment;
import com.example.accommodationbookingservice.dto.payment.PaymentDto;
import com.example.accommodationbookingservice.dto.payment.PaymentResponseDto;


import java.util.List;

public interface StripeService {

    PaymentResponseDto createPayment(CreatePayment createPayment);


}
