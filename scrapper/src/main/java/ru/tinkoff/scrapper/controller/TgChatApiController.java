package ru.tinkoff.scrapper.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.scrapper.api.TgChatApi;
import ru.tinkoff.scrapper.scheduler.LinkUpdaterScheduler;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class TgChatApiController implements TgChatApi {
    private static final Logger log = Logger.getLogger(LinkUpdaterScheduler.class.getName());
    @Override
    public void tgChatIdDelete(Long id) {
        log.log(Level.INFO, "delete tg chat request");
    }

    @Override
    public void tgChatIdPost(Long id) {
        log.log(Level.INFO, "add tg chat request");
    }
}
