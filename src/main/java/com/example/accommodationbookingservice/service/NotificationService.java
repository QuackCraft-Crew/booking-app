package com.example.accommodationbookingservice.service;

import com.example.accommodationbookingservice.model.Accommodation;
import com.example.accommodationbookingservice.model.Booking;

public interface NotificationService {
    void sendNotification(String notification);

    void sendBookingInfoCreation(Booking booking, Accommodation accommodation);

    void sendBookingInfoDeletion();

    void sendSuccessfulPaymentMessage();

    void sendFailedPaymentMessage();

    void sendMessageToAdminChat(String message);
}
