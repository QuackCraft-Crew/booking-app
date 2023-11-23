package com.example.accommodationbookingservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.accommodationbookingservice.dto.booking.BookingDto;
import com.example.accommodationbookingservice.dto.booking.BookingRequestDto;
import com.example.accommodationbookingservice.dto.booking.BookingUpdateDto;
import com.example.accommodationbookingservice.exception.NotAvailablePlacesToBook;
import com.example.accommodationbookingservice.mapper.BookingMapper;
import com.example.accommodationbookingservice.mapper.BookingMapperImpl;
import com.example.accommodationbookingservice.model.Accommodation;
import com.example.accommodationbookingservice.model.Address;
import com.example.accommodationbookingservice.model.Booking;
import com.example.accommodationbookingservice.model.User;
import com.example.accommodationbookingservice.repository.AccommodationRepository;
import com.example.accommodationbookingservice.repository.BookingRepository;
import com.example.accommodationbookingservice.security.CustomUserDetailsService;
import com.example.accommodationbookingservice.service.impl.BookingServiceImpl;
import com.example.accommodationbookingservice.service.impl.EmailSenderService;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {
    private static final Booking DEFAULT_BOOKING;
    private static final Accommodation DEFAULT_ACCOMMODATION;
    private static final Address DEFAULT_ADDRESS;
    private static final User DEFAULT_USER;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private AccommodationRepository accommodationRepository;
    @Mock
    private Authentication authentication;
    @Mock
    private EmailSenderService emailSenderService;
    @Mock
    private CustomUserDetailsService userDetailsService;
    @Mock
    private NotificationService notificationService;
    @Spy
    private BookingMapper bookingMapper = new BookingMapperImpl();
    @InjectMocks
    private BookingServiceImpl bookingService;

    static {
        DEFAULT_USER = new User();
        DEFAULT_USER.setId(1L);
        DEFAULT_USER.setEmail("testEmail");

        DEFAULT_ADDRESS = new Address();
        DEFAULT_ADDRESS.setId(1L);
        DEFAULT_ADDRESS.setCountry("Kyiv");
        DEFAULT_ADDRESS.setStreetName("Street");
        DEFAULT_ADDRESS.setStreetNumber("30");
        DEFAULT_ADDRESS.setCountry("Ukraine");

        DEFAULT_ACCOMMODATION = new Accommodation();
        DEFAULT_ACCOMMODATION.setId(1L);
        DEFAULT_ACCOMMODATION.setType(Accommodation.Type.HOTEL);
        DEFAULT_ACCOMMODATION.setAddress(DEFAULT_ADDRESS);
        DEFAULT_ACCOMMODATION.setAvailability(2);
        DEFAULT_ACCOMMODATION.setAmenities("something");
        DEFAULT_ACCOMMODATION.setDailyRate(BigDecimal.valueOf(1000));

        DEFAULT_BOOKING = new Booking();
        DEFAULT_BOOKING.setAccommodation(DEFAULT_ACCOMMODATION);
        DEFAULT_BOOKING.setUser(DEFAULT_USER);
        DEFAULT_BOOKING.setStatus(Booking.Status.PENDING);
    }

    @Test
    @DisplayName(value = "Create booking by invalid dates")
    void createBooking_InvalidDates_ThrowException() {
        BookingRequestDto requestDto = new BookingRequestDto(1L,
                LocalDate.of(2023, 11, 23), LocalDate.of(2023, 11, 22));
        DateTimeException actualException = assertThrows(DateTimeException.class, () ->
                bookingService.createBooking(requestDto, authentication));
        assertEquals("The check-in date must be earlier than the check-out",
                actualException.getMessage());
    }

    @Test
    @DisplayName(value = "Create booking without available places")
    void createBooking_NotMoreAvailablePlaces_ThrowException() {
        BookingRequestDto requestDto = new BookingRequestDto(1L,
                LocalDate.of(2023, 11, 23), LocalDate.of(2023, 11, 24));

        when(authentication.getName()).thenReturn("testEmail");
        when(userDetailsService.loadUserByUsername("testEmail")).thenReturn(DEFAULT_USER);
        when(accommodationRepository.getReferenceById(1L)).thenReturn(DEFAULT_ACCOMMODATION);
        when(bookingRepository.countAllByAccommodationIdAndDate(requestDto.accommodationId(),
                requestDto.checkInDate(), requestDto.checkOutDate())).thenReturn(
                        List.of(new Booking(), new Booking(), new Booking()));
        NotAvailablePlacesToBook notAvailablePlacesToBook =
                assertThrows(NotAvailablePlacesToBook.class, () ->
                bookingService.createBooking(requestDto, authentication));
        assertEquals("We haven't available places to book in this days",
                notAvailablePlacesToBook.getMessage());
    }

    @Test
    @DisplayName(value = "Create booking with valid input")
    void createBooking_ValidInput_CreateBooking() {
        BookingRequestDto requestDto = new BookingRequestDto(1L,
                LocalDate.of(2023, 11, 23), LocalDate.of(2023, 11, 24));
        Booking notSavedBooking = getDefaultBooking();
        notSavedBooking.setCheckIn(requestDto.checkInDate());
        notSavedBooking.setCheckOut(requestDto.checkOutDate());
        Booking savedBooking = getDefaultBooking();
        savedBooking.setId(1L);
        savedBooking.setCheckIn(requestDto.checkInDate());
        savedBooking.setCheckOut(requestDto.checkOutDate());

        when(authentication.getName()).thenReturn("testEmail");
        when(userDetailsService.loadUserByUsername("testEmail")).thenReturn(DEFAULT_USER);
        when(accommodationRepository.getReferenceById(1L)).thenReturn(DEFAULT_ACCOMMODATION);
        when(bookingRepository.countAllByAccommodationIdAndDate(
                requestDto.accommodationId(), requestDto.checkInDate(),
                requestDto.checkOutDate())).thenReturn(Collections.emptyList());
        when(bookingRepository.save(notSavedBooking)).thenReturn(savedBooking);
        when(accommodationRepository.findAccommodationByBookingId(1L))
                .thenReturn(DEFAULT_ACCOMMODATION);

        BookingDto actual = bookingService.createBooking(requestDto, authentication);
        verify(notificationService).sendBookingInfoCreation(notSavedBooking, DEFAULT_ACCOMMODATION);
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("status", Booking.Status.PENDING)
                .hasFieldOrPropertyWithValue("accommodationId", 1L)
                .hasFieldOrPropertyWithValue("userId", 1L);
    }

    @Test
    @DisplayName("Find all bookings by status id user id")
    void findByUserIdAndStatus_ValidInputWithoutUserId_ReturnList() {
        Booking b1 = getDefaultBooking();
        b1.setId(1L);
        Booking b2 = getDefaultBooking();
        b2.setId(2L);
        List<Booking> bookings = List.of(b1, b2);
        when(bookingRepository.findByStatus(Booking.Status.PENDING)).thenReturn(bookings);

        List<BookingDto> actual = bookingService.findByUserIdAndStatus(null,
                Booking.Status.PENDING, Pageable.ofSize(10));
        List<BookingDto> expected = bookings.stream()
                .map(bookingMapper::toBookingDto)
                .toList();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find all bookings by user id without status")
    void findByUserIdAndStatus_ValidInputWithoutStatus_ReturnList() {
        Long userId = 1L;
        Booking b1 = getDefaultBooking();
        b1.setId(1L);
        Booking b2 = getDefaultBooking();
        b2.setId(2L);
        List<Booking> bookings = List.of(b1, b2);
        when(bookingRepository.findByUserId(userId)).thenReturn(bookings);

        List<BookingDto> actual = bookingService.findByUserIdAndStatus(userId,
                null, Pageable.ofSize(10));
        List<BookingDto> expected = bookings.stream()
                .map(bookingMapper::toBookingDto)
                .toList();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Find all bookings by user id and status")
    void findByUserIdAndStatus_ValidInput_ReturnList() {
        Long userId = 1L;
        Booking b1 = getDefaultBooking();
        b1.setId(1L);
        Booking b2 = getDefaultBooking();
        b2.setId(2L);
        List<Booking> bookings = List.of(b1, b2);
        when(bookingRepository.findByUserIdAndStatus(userId, Booking.Status.PENDING))
                .thenReturn(bookings);

        List<BookingDto> actual = bookingService.findByUserIdAndStatus(userId,
                Booking.Status.PENDING, Pageable.ofSize(10));
        List<BookingDto> expected = bookings.stream()
                .map(bookingMapper::toBookingDto)
                .toList();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get all user's bookings")
    void getAll_ValidAuthentication_ReturnList() {
        Booking b1 = getDefaultBooking();
        b1.setId(1L);
        Booking b2 = getDefaultBooking();
        b2.setId(2L);
        List<Booking> bookings = List.of(b1, b2);

        when(authentication.getName()).thenReturn("testEmail");
        when(userDetailsService.loadUserByUsername("testEmail")).thenReturn(DEFAULT_USER);
        when(bookingRepository.findByUserId(DEFAULT_USER.getId())).thenReturn(bookings);
        List<BookingDto> actual = bookingService.getAll(Pageable.ofSize(10), authentication);
        List<BookingDto> expected = bookings.stream()
                .map(bookingMapper::toBookingDto)
                .toList();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Get booking by valid id")
    void getBookingById_ValidId_ReturnBooking() {
        Long bookingId = 1L;
        Booking booking = getDefaultBooking();
        booking.setId(bookingId);
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        BookingDto actual = bookingService.getBookingById(bookingId);
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("status", Booking.Status.PENDING);
    }

    @Test
    @DisplayName("Checks that getExpiredBookings method is called")
    void updateBookingStatusToExpired_ReturnExpiredBooks() {
        bookingService.getExpiredBookings();
        verify(bookingRepository).getExpiredBookings();
    }

    @Test
    @DisplayName("Update booking valid input")
    void updateBookingById_ValidInput_UpdateBooking() {
        Booking booking = getDefaultBooking();
        booking.setId(1L);
        BookingUpdateDto updateDto = new BookingUpdateDto(LocalDate.of(2023, 12, 22),
                LocalDate.of(2023, 12, 23), Booking.Status.CONFIRMED);
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        bookingMapper.updateBooking(updateDto, booking);
        when(bookingRepository.save(booking)).thenReturn(booking);
        BookingDto actual = bookingService.updateBookingById(1L, updateDto);
        assertThat(actual)
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("status", Booking.Status.CONFIRMED)
                .hasFieldOrPropertyWithValue("checkIn", LocalDate.of(2023, 12, 22))
                .hasFieldOrPropertyWithValue("checkOut", LocalDate.of(2023, 12, 23));
    }

    @Test
    @DisplayName("Delete booking valid id")
    void deleteById_ValidId_UpdateBookingStatus() {
        Long bookingId = 1L;
        Booking booking = getDefaultBooking();
        booking.setId(bookingId);
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        Booking deletedBooking = getDefaultBooking();
        deletedBooking.setId(bookingId);
        deletedBooking.setStatus(Booking.Status.CANCELED);

        bookingService.deleteById(bookingId);
        verify(notificationService).sendBookingInfoDeletion();

    }

    private Booking getDefaultBooking() {
        Booking booking = new Booking();
        booking.setAccommodation(DEFAULT_ACCOMMODATION);
        booking.setUser(DEFAULT_USER);
        booking.setStatus(Booking.Status.PENDING);
        return booking;
    }
}
