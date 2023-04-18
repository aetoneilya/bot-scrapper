package ru.tinkoff.bot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.bot.api.BotApi;
import ru.tinkoff.bot.controller.dto.request.UpdatesRequest;
import ru.tinkoff.bot.telegram.Bot;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequiredArgsConstructor
public class BotController implements BotApi {
    private static final Logger log = Logger.getLogger(BotController.class.getName());
    private final Bot bot;

    @Override
    public void updatesPost(UpdatesRequest request) {
        log.log(Level.INFO, "new update request " + request.description());
        bot.updateRequest(request.tgChatIds(), request.url(), request.description());
    }
}
