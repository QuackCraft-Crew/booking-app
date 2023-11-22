package com.example.accommodationbookingservice.dto.accommodation;

import com.example.accommodationbookingservice.model.Accommodation;
import com.example.accommodationbookingservice.model.Address;
import java.math.BigDecimal;

public record AccommodationDto(
        Long id,
        Accommodation.Type type,
        Long addressId,
        String size,
        String amenities,
        BigDecimal dailyRate,
        Integer availability
){
}
