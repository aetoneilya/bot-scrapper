package ru.tinkoff.scrapper.service.updatesender;

import ru.tinkoff.scrapper.client.tgbot.dto.request.UpdatesRequest;

public interface UpdateSender {
    void send(UpdatesRequest update);
}
