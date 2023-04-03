package ru.tinkoff.bot.telegram.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import ru.tinkoff.bot.client.scrapper.ScrapperClient;

@RequiredArgsConstructor
public class TrackCommand  implements Command{
    private final ScrapperClient scrapperClient;
    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "Add link";
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        scrapperClient.addLink(chatId, "http:testlink.com");
        return new SendMessage(chatId, "link added");
    }
}
