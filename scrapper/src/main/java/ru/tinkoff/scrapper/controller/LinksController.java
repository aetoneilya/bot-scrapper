package ru.tinkoff.scrapper.controller;

import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.scrapper.api.LinksApi;
import ru.tinkoff.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.scrapper.dto.request.RemoveLinkRequest;
import ru.tinkoff.scrapper.dto.response.LinkResponse;
import ru.tinkoff.scrapper.dto.response.ListLinksResponse;
import ru.tinkoff.scrapper.scheduler.LinkUpdaterScheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class LinksController implements LinksApi {
    private static final Logger log = Logger.getLogger(LinkUpdaterScheduler.class.getName());
    @Override
    public ListLinksResponse linksGet(Long tgChatId) {
        log.log(Level.INFO, "links get request");
        List<LinkResponse> listResponse = new ArrayList<>();

        listResponse.add(new LinkResponse(1L, "https://habr.com/ru/post/675716/"));
        listResponse.add(new LinkResponse(2L, "https://somelink"));
        listResponse.add(new LinkResponse(3L, "https://google.com"));
        return new ListLinksResponse(listResponse, listResponse.size());
    }

    @Override
    public LinkResponse linksPost(Long tgChatId, AddLinkRequest body) {
        log.log(Level.INFO, "add link request");
        return new LinkResponse(1L, body.link());
    }

    @Override
    public LinkResponse linksDelete(Long tgChatId, RemoveLinkRequest body) {
        log.log(Level.INFO, "remove link request");
        return new LinkResponse(tgChatId, body.link());
    }
}
