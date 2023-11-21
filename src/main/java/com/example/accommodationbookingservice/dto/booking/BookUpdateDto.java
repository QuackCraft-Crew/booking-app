package com.example.accommodationbookingservice.dto.booking;

import static com.example.accommodationbookingservice.model.Booking.Status;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record BookUpdateDto(
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate checkInDate,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate checkOutDate,
        Status status
) {
}
