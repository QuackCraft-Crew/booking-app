package com.example.accommodationbookingservice.controller;

import static com.example.accommodationbookingservice.model.Booking.Status;

import java.util.List;
import com.example.accommodationbookingservice.dto.booking.BookingDto;
import com.example.accommodationbookingservice.dto.booking.BookingRequestDto;
import com.example.accommodationbookingservice.dto.booking.BookingUpdateDto;
import com.example.accommodationbookingservice.service.BookingService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Tag(name = "Booking controller",description = "Endpoints for booking")
@RequestMapping(value = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingDto createBooking(@RequestBody BookingRequestDto bookingRequestDto) {
        return bookingService.createBooking(bookingRequestDto);
    }

    @GetMapping
    public List<BookingDto> getBookings(
            @RequestParam(value = "user_id", required = false) Long userId,
            @RequestParam(value = "status", required = false) Status status) {
        return bookingService.getBookings(userId, status);
    }

    @GetMapping("/my")
    public List<BookingDto> getUserBookings() {
        return bookingService.getUserBookings();
    }

    @GetMapping("/{id}")
    public BookingDto getBookingsById(@PathVariable @Positive Long id) {
        return bookingService.getBookingById(id);
    }

    @PutMapping("/{id}")
    public BookingDto updateBooking(
            @PathVariable Long id,
            @RequestBody BookingUpdateDto bookingUpdateDto) {
        return bookingService.updateBooking(id, bookingUpdateDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Positive Long id) {
        bookingService.deleteById(id);
    }

}
