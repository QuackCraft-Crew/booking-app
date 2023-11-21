package com.example.accommodationbookingservice.dto.booking;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

public record BookingUpdateDto(
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate checkIn,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate checkOut
) {
}
