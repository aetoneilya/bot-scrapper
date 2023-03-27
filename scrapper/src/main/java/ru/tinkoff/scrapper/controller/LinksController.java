package ru.tinkoff.scrapper.controller;

import org.springframework.http.ResponseEntity;
import ru.tinkoff.scrapper.api.LinksApi;
import ru.tinkoff.scrapper.dto.response.LinkResponse;
import ru.tinkoff.scrapper.dto.response.ListLinksResponse;

public class LinksController implements LinksApi {
    @Override
    public ListLinksResponse linksGet(Long tgChatId) {
        ListLinksResponse response = new ListLinksResponse();
        response.addLinksItem(new LinkResponse(1L, "https://habr.com/ru/post/675716/"));
        response.addLinksItem(new LinkResponse(2L, "https://somelink"));
        response.addLinksItem(new LinkResponse(3L, "https://google.com"));
        return response;
    }
}
