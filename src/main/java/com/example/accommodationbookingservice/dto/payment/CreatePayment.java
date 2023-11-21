package com.example.accommodationbookingservice.dto.payment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreatePayment {
    @Positive
    private BigDecimal amount;

    @NotBlank
    private String nameOfProduct;

    @NotBlank
    private String description;

    @Positive
    private Long bookingId;
}
