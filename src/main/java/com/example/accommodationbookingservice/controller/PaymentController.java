package com.example.accommodationbookingservice.controller;

import com.example.accommodationbookingservice.dto.payment.CreatePayment;
import com.example.accommodationbookingservice.dto.payment.PaymentResponseDto;
import com.example.accommodationbookingservice.service.impl.StripeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    @Autowired
    private StripeService stripeService;

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
