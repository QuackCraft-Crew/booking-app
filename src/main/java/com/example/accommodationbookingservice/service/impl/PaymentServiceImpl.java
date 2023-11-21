package com.example.accommodationbookingservice.service.impl;

import com.example.accommodationbookingservice.model.Payment;
import com.example.accommodationbookingservice.model.PaymentStatus;
import com.example.accommodationbookingservice.repository.PaymentRepository;
import com.example.accommodationbookingservice.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@Data
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public String createPaymentIntent(Long bookingId, BigDecimal amount) {

        Payment payment = new Payment();
        payment.setBookingId(bookingId);
        payment.setAmountToPay(amount);
        payment.setStatus(PaymentStatus.PENDING);

       // paymentRepository.save(payment);

        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount.multiply(BigDecimal.valueOf(100)));


        try {
            PaymentIntent intent = PaymentIntent.create(params);
            payment.setSessionUrl(intent.getPaymentMethodTypes().get(0));
            payment.setSessionId(intent.getId());



            return intent.getClientSecret();
        } catch (StripeException e) {

            return "huyna";
        }
    }
}
