package ru.tinkoff.scrapper.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.scrapper.api.TgChatApi;
import ru.tinkoff.scrapper.exception.ResourceNotFoundException;

@RestController
public class TgChatApiController implements TgChatApi {
    @Override
    public void tgChatIdDelete(Long id) {

    }

    @Override
    public void tgChatIdPost(Long id) {
    }
}
