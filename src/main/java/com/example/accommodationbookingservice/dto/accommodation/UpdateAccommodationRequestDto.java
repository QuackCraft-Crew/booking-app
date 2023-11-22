package com.example.accommodationbookingservice.dto.accommodation;

import com.example.accommodationbookingservice.model.Accommodation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record UpdateAccommodationRequestDto (
    Accommodation.Type type,
    Long addressId,
    @NotBlank
    String size,
    @NotBlank
    String amenities,
    @NotNull @Positive
    BigDecimal dailyRate,
    @NotNull @Positive
    Integer availability
){}
