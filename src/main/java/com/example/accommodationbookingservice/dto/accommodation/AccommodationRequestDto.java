package com.example.accommodationbookingservice.dto.accommodation;

import com.example.accommodationbookingservice.model.Accommodation;
import com.example.accommodationbookingservice.model.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record AccommodationRequestDto(
        Accommodation.Type type,
        Address address,
        String map_Url,
        @NotBlank
        String size,
        @NotBlank
        String amenities,
        @NotNull @Positive
        BigDecimal dailyRate,
        @NotNull @Positive
        Integer availability
){
}
