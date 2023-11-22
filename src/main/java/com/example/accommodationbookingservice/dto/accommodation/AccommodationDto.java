package com.example.accommodationbookingservice.dto.accommodation;

import com.example.accommodationbookingservice.model.Accommodation;
import com.example.accommodationbookingservice.model.Address;
import com.example.accommodationbookingservice.util.MapUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Objects;

@Data
public final class AccommodationDto {
    private  Long id;
    private  Accommodation.Type type;
    private  Address address;
    private  String size;
    private  String amenities;
    private  BigDecimal dailyRate;
    private  Integer availability;
    private  String map_Url;
}
