package ru.tinkoff.bot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.bot.api.BotApi;
import ru.tinkoff.bot.controller.dto.request.UpdatesRequest;
import ru.tinkoff.bot.telegram.Bot;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BotController implements BotApi {
    private final Bot bot;

    @Override
    public void updatesPost(UpdatesRequest request) {
        log.info("new update request " + request.description());
        bot.updateRequest(request.tgChatIds(), request.url(), request.description());
    }
}
