package com.example.accommodationbookingservice.controller;

import com.example.accommodationbookingservice.dto.payment.CreatePayment;
import com.example.accommodationbookingservice.dto.payment.CreatePaymentResponseDto;
import com.example.accommodationbookingservice.dto.payment.PaymentResponseDto;
import com.example.accommodationbookingservice.exception.EntityNotFoundException;
import com.example.accommodationbookingservice.model.User;
import com.example.accommodationbookingservice.repository.UserRepository;
import com.example.accommodationbookingservice.service.PaymentService;
import com.example.accommodationbookingservice.service.impl.StripeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@Tag(name = "Payment management", description = "Endpoints for managing payments")
public class PaymentController {
    @Autowired
    private StripeService stripeService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserRepository userRepository;


    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get payments for logged in user",
            description = "Get all payments by user id")
    public List<PaymentResponseDto> getPaymentsByUserId(Principal principal) {
        return paymentService.getPaymentsByUserId(getUserId(principal));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get payments by user id",
            description = "Get all payments by user id")
    public List<PaymentResponseDto> getPaymentsByUserId(@PathVariable Long userId) {
        return paymentService.getPaymentsByUserId(userId);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Create payment")
    @ResponseStatus(HttpStatus.CREATED)
    public CreatePaymentResponseDto createPaymentIntent(
            @RequestBody @Valid CreatePayment createPayment) {
        return stripeService.createPaymentSession(createPayment);
    }

    @GetMapping("/success")
    @Operation(summary = "Success payment",
            description = "This endpoint will be reached if the payment is successful")
    public String getSuccessfulResponse(@RequestParam("session_id") String sessionId) {
        paymentService.successPayment(sessionId);
        return "success  payment with session id: " + sessionId;
    }

    @GetMapping("/cancel")
    @Operation(summary = "Cancel payment",
            description = "This endpoint will be reached if the payment is canceled")
    public String getCancelResponse(@RequestParam("session_id") String sessionId) {
        paymentService.cancelPayment(sessionId);
        return "cancel payment with session id: " + sessionId;
    }

    private Long getUserId(Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new EntityNotFoundException("User is not found"));
        return user.getId();
    }
}
