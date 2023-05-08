package ru.tinkoff.scrapper.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.scrapper.api.TgChatApi;
import ru.tinkoff.scrapper.scheduler.LinkUpdaterScheduler;
import ru.tinkoff.scrapper.service.TgChatService;

@RestController
@RequiredArgsConstructor
public class TgChatApiController implements TgChatApi {
    private static final Logger LOG = Logger.getLogger(LinkUpdaterScheduler.class.getName());
    private final TgChatService service;

    @Override
    public void tgChatIdDelete(Long id) {
        LOG.log(Level.INFO, "delete tg chat " + id);
        service.unregister(id);
    }

    @Override
    public void tgChatIdPost(Long id) {
        LOG.log(Level.INFO, "add tg chat " + id);
        service.register(id);
    }
}
