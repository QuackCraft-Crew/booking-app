package com.example.accommodationbookingservice.service;

import static com.example.accommodationbookingservice.model.Booking.Status;

import com.example.accommodationbookingservice.dto.booking.BookingDto;
import com.example.accommodationbookingservice.dto.booking.BookingRequestDto;
import com.example.accommodationbookingservice.dto.booking.BookingUpdateDto;
import com.example.accommodationbookingservice.model.Accommodation;
import com.example.accommodationbookingservice.model.Booking;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface BookingService {

    BookingDto createBooking(BookingRequestDto booking, Authentication authentication);

    boolean isAvailableAccommodation(Accommodation accommodation,
                                     LocalDate from, LocalDate to);

    List<BookingDto> findByUserIdAndStatus(Long userId, Status status, Pageable pageable);

    List<BookingDto> getAll(Pageable pageable, Authentication authentication);

    BookingDto getBookingById(Long id);

    BookingDto updateBookingById(Long id, BookingUpdateDto updateDto);

    void deleteById(Long id);

    List<Booking> getExpiredBookings();

    void updateBookingStatusToExpired(List<Booking> bookings);
}
