package ru.tinkoff.bot.telegram.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import ru.tinkoff.bot.client.scrapper.ScrapperClient;
import ru.tinkoff.bot.client.scrapper.dto.response.LinkResponse;
import ru.tinkoff.bot.client.scrapper.dto.response.ListLinksResponse;

@RequiredArgsConstructor
public class ListCommand implements Command {
    private final ScrapperClient scrapperClient;

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "List of all tracked links";
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();
        ListLinksResponse response = scrapperClient.getAllLinks(chatId);

        if (response.size() == 0) return new SendMessage(chatId, "No tracked links yet..\nFeel free to add some");

        StringBuilder sendMessageText = new StringBuilder("Tracked links:");
        for (LinkResponse linkResponse : response.links()) {
            sendMessageText.append("\n").append(linkResponse.url().toString());
        }

        return new SendMessage(chatId, sendMessageText.toString());
    }
}
