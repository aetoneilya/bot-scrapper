package ru.tinkoff.bot.telegram.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import ru.tinkoff.bot.client.scrapper.ScrapperClient;

@RequiredArgsConstructor
public class StartCommand implements Command {
    private final ScrapperClient scrapperClient;

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "register";
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        scrapperClient.addChat(chatId);
        return new SendMessage(chatId, "You are registered! Add some github or stackoverflow links");
    }
}
