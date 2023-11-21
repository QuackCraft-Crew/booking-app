package com.example.accommodationbookingservice.service.impl;

import static com.example.accommodationbookingservice.model.Booking.Status;
import com.example.accommodationbookingservice.dto.booking.BookingDto;
import com.example.accommodationbookingservice.dto.booking.BookingRequestDto;
import com.example.accommodationbookingservice.dto.booking.BookingUpdateDto;
import com.example.accommodationbookingservice.mapper.BookingMapper;
import com.example.accommodationbookingservice.model.Booking;
import com.example.accommodationbookingservice.model.User;
import com.example.accommodationbookingservice.repository.BookingRepository;
import com.example.accommodationbookingservice.repository.UserRepository;
import com.example.accommodationbookingservice.security.CustomUserDetailsService;
import com.example.accommodationbookingservice.service.BookingService;
import java.util.Collections;
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
    //paymnet service

    @Override
    public BookingDto createBooking(BookingRequestDto requestDto, Authentication authentication) {
        User user = getUser(authentication);
        Booking booking = bookingMapper.toBookingModel(requestDto);
        booking.setUser(user);
        booking.setStatus(Status.PENDING);
        return bookingMapper.toBookingDto(bookingRepository.save(booking));
    }

    //endpoint for admin who creates bookings for other users

    @Override
    public List<BookingDto> findByUserIdAndStatus(Long userId, Status status, Pageable pageable, Authentication authentication) {
        return null;

    }

    @Override
    public List<BookingDto> getAll(Pageable pageable, Authentication authentication) {
        /*User user = getUser(authentication);
        user.findByUserId(user.getId());*/
        return Collections.emptyList();
    }

    @Override
    public BookingDto getBookingById(Long id, Authentication authentication) {
        return null;
    }

    @Override
    public BookingDto updateBookingById(Long id, BookingUpdateDto updateDto, Authentication authentication) {
        return null;
    }


    @Override
    public void deleteById(Long id, Authentication authentication) {

    }

    private User getUser(Authentication authentication) {
        return (User) userDetailsService.loadUserByUsername(authentication.getName());
    }

}
