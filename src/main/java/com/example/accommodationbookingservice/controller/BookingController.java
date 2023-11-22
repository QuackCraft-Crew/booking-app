package com.example.accommodationbookingservice.controller;

import static com.example.accommodationbookingservice.model.Booking.Status;

import com.example.accommodationbookingservice.dto.booking.BookingDto;
import com.example.accommodationbookingservice.dto.booking.BookingRequestDto;
import com.example.accommodationbookingservice.dto.booking.BookingUpdateDto;
import com.example.accommodationbookingservice.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Tag(name = "Booking controller", description = "Endpoints for booking")
@RequestMapping(value = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingDto createBooking(@RequestBody BookingRequestDto bookingRequestDto, Authentication authentication) {
        return bookingService.createBooking(bookingRequestDto, authentication);
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Create booking",
            description = "Create new booking by accommodation check in and check out dates")
    public BookingDto createBooking(
            @RequestBody BookingRequestDto bookingRequestDto,
            Authentication authentication) {
        return bookingService.createBooking(bookingRequestDto, authentication);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get bookings",
            description = "GEt booking by user id and status of booking")
    public List<BookingDto> getBookings(
            @RequestParam(value = "user_id", required = false) Long userId,
            @RequestParam(value = "status", required = false) Status status,
            @PageableDefault(size = 20, sort = "title",
                    direction = Sort.Direction.ASC) Pageable pageable) {
        return bookingService.findByUserIdAndStatus(userId, status, pageable);
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get bookings",
            description = "Get booking by user id and sorting by status")
    public List<BookingDto> getUserBookings(
            @PageableDefault(size = 20, sort = "status",
                    direction = Sort.Direction.ASC) Pageable pageable,
                    Authentication authentication) {
        return bookingService.getAll(pageable, authentication);      
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @Operation(summary = "Get booking",
            description = "Get booking by booking id")
    public BookingDto getBookingsById(@PathVariable @Positive Long id) {
        return bookingService.getBookingById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update booking")
    public BookingDto updateBooking(
            @PathVariable Long id,
            @RequestBody BookingUpdateDto bookingUpdateDto) {
        return bookingService.updateBookingById(id, bookingUpdateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete booking")
    public void delete(
            @PathVariable @Positive Long id) {
        bookingService.deleteById(id);
    }

}
