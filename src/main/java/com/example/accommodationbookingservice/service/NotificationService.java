package com.example.accommodationbookingservice.service;

import com.example.accommodationbookingservice.model.Accommodation;
import com.example.accommodationbookingservice.model.Booking;

public interface NotificationService {
    void sendNotification(String notification);

    void sendBookingInfoCreation(Booking booking, Accommodation accommodation);

    void sendBookingInfoDeleting(Booking booking);

    void sendSuccessfulPaymentMessage();

    void sendFailedPaymentMessage();
}
