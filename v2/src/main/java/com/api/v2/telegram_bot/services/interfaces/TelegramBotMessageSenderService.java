package com.api.v2.telegram_bot.services.interfaces;

import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface TelegramBotMessageSenderService {
    void sendMessage(String message) throws TelegramApiException;
}
