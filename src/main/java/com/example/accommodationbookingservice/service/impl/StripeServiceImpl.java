package com.example.accommodationbookingservice.service.impl;

import com.example.accommodationbookingservice.dto.payment.CreatePayment;
import com.example.accommodationbookingservice.dto.payment.PaymentDto;
import com.example.accommodationbookingservice.dto.payment.PaymentResponseDto;
import com.example.accommodationbookingservice.mapper.PaymentMapper;
import com.example.accommodationbookingservice.repository.PaymentRepository;
import com.example.accommodationbookingservice.service.StripeService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.model.checkout.Session;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StripeServiceImpl implements StripeService {

    private final PaymentRepository paymentRepository;
    private static final String SUCCESS_URL = "http://localhost:8080/success";
    private static final String CANCEL_URL = "http://localhost:8080/cancel";
    @Value("${stripe.secret.key}")
    private String secretKey;

    @PostConstruct
    void init() {
        Stripe.apiKey = secretKey;
    }

    @Override
    public PaymentResponseDto createPayment(CreatePayment createPayment) {
        SessionCreateParams.Builder builder = new SessionCreateParams.Builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount(createPayment.getAmount().longValue() * 100L)
                                .setProductData(
                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                .setName(createPayment.getNameOfProduct())
                                                .setDescription(createPayment.getDescription())
                                                .build())
                                .build()
                        ).setQuantity(1L)
                        .build()
                ).setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(SUCCESS_URL + "?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(CANCEL_URL);
        Session session;
        try {
            session = Session.create(builder.build());
        } catch (StripeException e) {
            throw new RuntimeException("Can't create payment session", e);
        }
        return new PaymentResponseDto(session.getUrl());
    }



}
