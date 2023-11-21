package com.example.accommodationbookingservice.dto.booking;

import static com.example.accommodationbookingservice.model.Booking.Status;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

public record BookUpdateDto(
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate checkIn,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate checkOut,
        Status status
) {
}
