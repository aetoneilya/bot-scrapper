package ru.tinkoff.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.bot.client.scrapper.ScrapperClient;
import ru.tinkoff.bot.telegram.command.*;

import java.util.ArrayList;
import java.util.List;

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

    @Bean
    List<Command> getCommands() {
        List<Command> commands = new ArrayList<>();
        commands.add(new UntrackCommand(getScrapperClient()));
        commands.add(new TrackCommand(getScrapperClient()));
        commands.add(new StartCommand(getScrapperClient()));
        commands.add(new ListCommand(getScrapperClient()));
        commands.add(new HelpCommand(commands));
        return commands;
    }
}
