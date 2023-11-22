package com.example.accommodationbookingservice.service;

public interface NotificationService {
    void sendNotification(String notification, String chatId);
    
    void sendMessageToAdminChat(String message);
}
