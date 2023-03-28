package ru.tinkoff.bot.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.bot.api.BotApi;
import ru.tinkoff.bot.dto.request.UpdatesRequest;

@RestController
public class BotController implements BotApi {
    @Override
    public void updatesPost(UpdatesRequest request) {
    }
}
