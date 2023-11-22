package com.example.accommodationbookingservice.service.impl;

import static com.example.accommodationbookingservice.model.Booking.Status;

import com.example.accommodationbookingservice.dto.booking.BookUpdateDto;
import com.example.accommodationbookingservice.dto.booking.BookingDto;
import com.example.accommodationbookingservice.dto.booking.BookingRequestDto;
import com.example.accommodationbookingservice.exception.EntityNotFoundException;
import com.example.accommodationbookingservice.mapper.BookingMapper;
import com.example.accommodationbookingservice.model.Booking;
import com.example.accommodationbookingservice.model.User;
import com.example.accommodationbookingservice.repository.BookingRepository;
import com.example.accommodationbookingservice.security.CustomUserDetailsService;
import com.example.accommodationbookingservice.service.BookingService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final CustomUserDetailsService userDetailsService;

    @Override
    @Transactional
    public BookingDto createBooking(BookingRequestDto requestDto, Authentication authentication) {
        User user = getUser(authentication);
        Booking booking = bookingMapper.toBookingModel(requestDto);
        booking.setUser(user);
        booking.setStatus(Status.PENDING);
        return bookingMapper.toBookingDto(bookingRepository.save(booking));
    }

    //endpoint for admin who creates bookings for other users

    @Override
    public List<BookingDto> findByUserIdAndStatus(
            Long userId, Status status, Pageable pageable) {
        List<Booking> bookings = bookingRepository.findByUserIdAndStatus(userId, status);
        return bookings.stream()
                .map(bookingMapper::toBookingDto)
                .toList();
    }

    @Override
    public List<BookingDto> getAll(Pageable pageable, Authentication authentication) {
        User user = getUser(authentication);

        return bookingRepository.findByUserId(user.getId()).stream()
                .map(bookingMapper::toBookingDto)
                .toList();
    }

    @Override
    public BookingDto getBookingById(Long id) {
        return bookingMapper.toBookingDto(bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cannot find booking by id " + id)
                ));
    }

    @Override
    public BookingDto updateBookingById(Long id, BookUpdateDto requestDto) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can`t find booking by id " + id)
                );
        bookingMapper.updateBooking(requestDto, booking);
        booking.setStatus(requestDto.status());
        return bookingMapper.toBookingDto(bookingRepository.save(booking));
    }

    @Override
    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }

    private User getUser(Authentication authentication) {
        return (User) userDetailsService.loadUserByUsername(authentication.getName());
    }
}
