package com.example.accommodationbookingservice.dto.booking;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

public record BookingRequestDto(
        Long accommodationId,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate checkInDate,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate checkOutDate
) {
}
