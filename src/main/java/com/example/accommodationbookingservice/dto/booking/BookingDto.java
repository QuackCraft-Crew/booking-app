package com.example.accommodationbookingservice.dto.booking;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import static com.example.accommodationbookingservice.model.Booking.Status;

public record BookingDto (
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
