package ru.tinkoff.scrapper.service.updatesender;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.scrapper.client.tgbot.TelegramBotClient;
import ru.tinkoff.scrapper.client.tgbot.dto.request.UpdatesRequest;

@RequiredArgsConstructor
public class HttpUpdateSender implements UpdateSender {
    private final TelegramBotClient telegramBotClient;

    @Override
    public void send(UpdatesRequest update) {
        telegramBotClient.update(update);
    }
}
