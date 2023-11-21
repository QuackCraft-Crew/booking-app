package com.example.accommodationbookingservice.controller;

import com.example.accommodationbookingservice.dto.payment.CreatePayment;
import com.example.accommodationbookingservice.dto.payment.PaymentDto;
import com.example.accommodationbookingservice.dto.payment.PaymentResponseDto;
import com.example.accommodationbookingservice.model.Payment;
import com.example.accommodationbookingservice.service.PaymentService;
import com.example.accommodationbookingservice.service.StripeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaymentController {

    private PaymentService paymentService;
    private StripeService stripeService;

    @GetMapping("/{id}")
    public List<PaymentDto> getPaymentsByUserId(@RequestParam("id") Long userId) {
        return paymentService.getPaymentsByUserId(userId);
    }

    @PostMapping("/create-payment-intent")
    @ResponseBody
    public PaymentResponseDto createPaymentIntent(@RequestBody @Valid CreatePayment createPayment) {
        return stripeService.createPayment(createPayment);
    }

    @GetMapping("/success")
    public String getSuccessfulResponse(@RequestParam("session_id") String sessionId) {
        System.out.println("success " + sessionId);
        return sessionId;
    }

    @GetMapping("/cancel")
    public String getCancelResponse() {
        System.out.println("cancel");
        return "CANCEL";
    }
}
