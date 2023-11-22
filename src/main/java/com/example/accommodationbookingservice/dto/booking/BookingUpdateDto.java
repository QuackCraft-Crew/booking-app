package com.example.accommodationbookingservice.dto.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record BookingUpdateDto(
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate checkIn,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate checkOut
) {
}
