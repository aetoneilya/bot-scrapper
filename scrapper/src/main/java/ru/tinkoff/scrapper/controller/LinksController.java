package ru.tinkoff.scrapper.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.scrapper.api.LinksApi;
import ru.tinkoff.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.scrapper.dto.request.RemoveLinkRequest;
import ru.tinkoff.scrapper.dto.response.LinkResponse;
import ru.tinkoff.scrapper.dto.response.ListLinksResponse;

@RestController
public class LinksController implements LinksApi {
    @Override
    public ListLinksResponse linksGet(Long tgChatId) {
        ListLinksResponse response = new ListLinksResponse();
        response.addLinksItem(new LinkResponse(1L, "https://habr.com/ru/post/675716/"));
        response.addLinksItem(new LinkResponse(2L, "https://somelink"));
        response.addLinksItem(new LinkResponse(3L, "https://google.com"));
        return response;
    }

    @Override
    public LinkResponse linksPost(Long tgChatId, AddLinkRequest body) {
        LinkResponse response = new LinkResponse(1L, body.getLink());
        return response;
    }

    @Override
    public LinkResponse linksDelete(Long tgChatId, RemoveLinkRequest body) {
        LinkResponse response = new LinkResponse(tgChatId, body.getLink());
        return response;
    }
}
