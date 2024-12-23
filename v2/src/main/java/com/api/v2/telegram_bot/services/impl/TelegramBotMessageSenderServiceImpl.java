package com.api.v2.telegram_bot.services.impl;

import com.api.v2.telegram_bot.bot.TelegramBot;
import com.api.v2.telegram_bot.services.interfaces.TelegramBotMessageSenderService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class TelegramBotMessageSenderServiceImpl implements TelegramBotMessageSenderService {

    private final TelegramBot telegramBot;

    public TelegramBotMessageSenderServiceImpl(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    @Override
    public void sendMessage(String message) throws TelegramApiException {
        telegramBot.execute(new SendMessage("209797404", message));
    }

}
