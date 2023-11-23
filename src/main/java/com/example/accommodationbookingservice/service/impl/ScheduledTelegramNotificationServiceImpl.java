package com.example.accommodationbookingservice.service.impl;

import com.example.accommodationbookingservice.model.Booking;
import com.example.accommodationbookingservice.service.BookingService;
import com.example.accommodationbookingservice.service.NotificationService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduledTelegramNotificationServiceImpl {
    private static final String SCHEDULE = "0 0 0 * * *"; //every day at midnight

    private final NotificationService notificationService;
    private final BookingService bookingService;

    @Transactional
    @Scheduled(cron = SCHEDULE)
    public void reportCurrentTime() {
        List<Booking> expiredBookings = bookingService.getExpiredBookings();
        if (!expiredBookings.isEmpty()) {
            bookingService.updateBookingStatusToExpired(expiredBookings);

            String message = expiredBookings.stream()
                    .map(this::createBookingInfo)
                    .collect(Collectors.joining());

            notificationService.sendMessageToAdminChat(message);
        } else {
            notificationService.sendMessageToAdminChat("No expired bookings today!");
        }
    }

    private String createBookingInfo(Booking booking) {
        String bookingInfo =
                """
                Accommodation type: %s
                Address: %s %s
                Country: %s
                Check-in time: %s
                Checkout time: %s
                User: %s %s
                Email: %s
                -----------
                """;

        return String.format(bookingInfo,
                booking.getAccommodation().getType(),
                booking.getAccommodation().getAddress().getStreetName(),
                booking.getAccommodation().getAddress().getStreetNumber(),
                booking.getAccommodation().getAddress().getCountry(),
                booking.getCheckIn(),
                booking.getCheckOut(),
                booking.getUser().getFirstName(),
                booking.getUser().getLastName(),
                booking.getUser().getEmail());
    }
}
