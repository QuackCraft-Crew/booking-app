package com.example.accommodationbookingservice.dto.booking;

import static com.example.accommodationbookingservice.model.Booking.Status;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record BookingDto(
        Long id,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate checkIn,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate checkOut,
        Long accommodationId,
        Long userId,
        Status status
) {
}
