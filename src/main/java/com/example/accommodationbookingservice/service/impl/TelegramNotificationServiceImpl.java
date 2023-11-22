package com.example.accommodationbookingservice.service.impl;

import com.example.accommodationbookingservice.exception.CustomTelegramApiException;
import com.example.accommodationbookingservice.service.NotificationService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Service
public class TelegramNotificationServiceImpl extends TelegramLongPollingBot
        implements NotificationService {
    private static final String START_COMMAND = "/start";

    @Value("${bot.token}")
    private String token;

    @Value("${bot.name}")
    private String name;

    @Value("${bot.chatId}")
    private String chatId;

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void sendNotification(String notification, String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(notification);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new CustomTelegramApiException("Can't send message: ", e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            String userId = message.getChatId().toString();
            String firstName = message.getFrom().getFirstName();
            if (message.getText().equals(START_COMMAND)) {
                startCommandResponse(userId, firstName);
            } else {
                sendMessage(userId, "This command is currently unavailable!");
            }
        }
    }

    @PostConstruct
    public void init() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            throw new CustomTelegramApiException("Can't register bot :", e);
        }
    }

    @Override
    public void sendMessageToAdminChat(String message) {
        sendNotification(message, chatId);
    }

    private void startCommandResponse(String chatId, String firstName) {
        String answer = "Hello, " + firstName + ", nice to meet you!";
        sendMessage(chatId, answer);
    }

    private void sendMessage(String chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new CustomTelegramApiException("Message was not send: ", e);
        }
    }
}
