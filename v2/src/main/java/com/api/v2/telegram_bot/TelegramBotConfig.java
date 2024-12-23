package com.api.v2.telegram_bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class TelegramBotConfig {

    @Bean
    public TelegramBot config(
            @Value("${bot.name}") String botName,
            @Value("${bot.token}") String token
    ) throws TelegramApiException {
        TelegramBot telegramBot = new TelegramBot(botName, token);
        TelegramBotsApi telegramBotApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotApi.registerBot(telegramBot);
        return telegramBot;
    }
}
