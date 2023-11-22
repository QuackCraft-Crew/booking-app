package com.example.accommodationbookingservice.dto.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record BookingRequestDto(
        Long accommodationId,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate checkInDate,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate checkOutDate
) {
}
