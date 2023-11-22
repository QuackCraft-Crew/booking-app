package com.example.accommodationbookingservice.service;

import com.example.accommodationbookingservice.repository.BookingRepository;
import com.example.accommodationbookingservice.service.impl.PaymentServiceImpl;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {
    @Mock
    private BookingRepository bookingRepository;
    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    @DisplayName("")
    void isAvailableAccommodation_NoMorePlaces_False() {
        LocalDate from = LocalDate.parse("2023-11-23");
        LocalDate to = LocalDate.parse("2023-11-24");

    }
}
