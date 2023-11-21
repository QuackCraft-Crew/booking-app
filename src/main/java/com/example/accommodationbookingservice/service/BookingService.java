package com.example.accommodationbookingservice.service;

import com.example.accommodationbookingservice.dto.booking.BookingDto;
import com.example.accommodationbookingservice.dto.booking.BookingRequestDto;
import com.example.accommodationbookingservice.dto.booking.BookingUpdateDto;
import com.example.accommodationbookingservice.model.Booking;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface BookingService {

    BookingDto createBooking(BookingRequestDto booking, Authentication authentication);

    List<BookingDto> findByUserIdAndStatus(Long userId, Status status, Pageable pageable, Authentication authentication);

    List<BookingDto> getAll(Pageable pageable, Authentication authentication);

    BookingDto getBookingById(Long id, Authentication authentication);

    BookingDto updateBookingById(Long id, BookingUpdateDto updateDto, Authentication authentication);

    void deleteById(Long id, Authentication authentication);
}
