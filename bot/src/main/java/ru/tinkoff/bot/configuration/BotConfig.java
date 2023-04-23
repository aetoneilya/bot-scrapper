package ru.tinkoff.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.bot.client.scrapper.ScrapperClient;

@Validated
@Configuration
public class BotConfig {
    @NotNull
    @Value("${bot.telegram-token}")
    String telegramToken;

    @NotNull
    @Value("${bot.base-url-scrapper}")
    String baseUrlScrapper;

    @Bean
    TelegramBot getTelegramBot() {
        return new TelegramBot(telegramToken);
    }

    @Bean
    ScrapperClient getScrapperClient() {
        return new ScrapperClient(baseUrlScrapper);
    }
}
