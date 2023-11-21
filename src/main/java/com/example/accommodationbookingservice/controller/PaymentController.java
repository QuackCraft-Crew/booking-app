package com.example.accommodationbookingservice.controller;

import com.example.accommodationbookingservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping()
    public String createPaymentIntent(@RequestParam Long bookingId, @RequestParam BigDecimal amount) {
        return paymentService.createPaymentIntent(bookingId, amount);
    }

}
