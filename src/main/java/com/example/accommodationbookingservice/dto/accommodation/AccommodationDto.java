package com.example.accommodationbookingservice.dto.accommodation;

import com.example.accommodationbookingservice.model.Accommodation;
import com.example.accommodationbookingservice.model.Address;
import java.math.BigDecimal;
import lombok.Data;

@Data
public final class AccommodationDto {
    private Long id;
    private Accommodation.Type type;
    private Address address;
    private String size;
    private String amenities;
    private BigDecimal dailyRate;
    private Integer availability;
    private String mapUrl;
}
