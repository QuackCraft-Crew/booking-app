package com.example.accommodationbookingservice.service.impl;

import com.example.accommodationbookingservice.dto.payment.CreatePayment;
import com.example.accommodationbookingservice.dto.payment.CreatePaymentResponseDto;
import com.example.accommodationbookingservice.model.Booking;
import com.example.accommodationbookingservice.model.Payment;
import com.example.accommodationbookingservice.repository.BookingRepository;
import com.example.accommodationbookingservice.repository.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {
    @Value("${payment.success.url}")
    private String successUrl;
    @Value("${payment.cancel.url}")
    private String cancelUrl;
    @Value("${stripe.secret.key}")
    private String secretKey;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @PostConstruct
    void init() {
        Stripe.apiKey = secretKey;
    }

    public CreatePaymentResponseDto createPaymentSession(CreatePayment createPayment) {
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
                .setSuccessUrl(successUrl + "?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(cancelUrl + "?session_id={CHECKOUT_SESSION_ID}");
        Session session;
        try {
            session = Session.create(builder.build());
        } catch (StripeException e) {
            throw new RuntimeException("Can't create payment session", e);
        }
        savePayment(session, createPayment);
        return new CreatePaymentResponseDto(session.getUrl());
    }

    private void savePayment(Session session, CreatePayment createPayment) {
        Booking booking = bookingRepository.getReferenceById(createPayment.getBookingId());
        Payment payment = new Payment();
        payment.setStatus(Payment.Status.PENDING);
        payment.setBooking(booking);
        payment.setSessionId(session.getId());
        payment.setSessionUrl(session.getUrl());
        payment.setAmountToPay(createPayment.getAmount());
        paymentRepository.save(payment);
    }
}
