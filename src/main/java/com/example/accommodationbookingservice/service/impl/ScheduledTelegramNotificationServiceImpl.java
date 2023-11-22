package com.example.accommodationbookingservice.service.impl;

import com.example.accommodationbookingservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduledTelegramNotificationServiceImpl {
    private static String schedule;

    private final NotificationService notificationService;
    //private final BookingService bookingService;

    public void reportCurrentTime() {

    }

    private String createBookingInfo() {
        return "";
    }
}
