package com.example.accommodationbookingservice.repository;

import com.example.accommodationbookingservice.model.Booking;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookingRepositoryTest {
    @Autowired
    private BookingRepository bookingRepository;

    @Test
    @Sql(scripts = {"classpath:database/addresses/insert-address.sql",
            "classpath:database/accommodations/insert-accommodation.sql",
            "classpath:database/users/insert-users.sql",
            "classpath:database/bookings/insert-bookings.sql",
            "classpath:database/payments/insert-payments.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @DisplayName("Get user bookings by valid user id")
    void getBookingByValidUserId_ReturnList() {
        Long userId = 1L;
        List<Booking> actual = bookingRepository.findByUserId(userId);
        Assertions.assertEquals(4, actual.size());
    }

    @Test
    @Sql(scripts = {"classpath:database/addresses/insert-address.sql",
            "classpath:database/accommodations/insert-accommodation.sql",
            "classpath:database/users/insert-users.sql",
            "classpath:database/bookings/insert-bookings.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @DisplayName("Get all bookings by status")
    void getBookingByStatus_ValidUserId_ReturnList() {
        List<Booking> canceledBooking = bookingRepository.findByStatus(Booking.Status.CANCELED);
        Assertions.assertEquals(2, canceledBooking.size());
    }
}
