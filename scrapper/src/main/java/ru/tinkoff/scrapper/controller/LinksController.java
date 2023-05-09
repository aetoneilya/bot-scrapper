package ru.tinkoff.scrapper.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.scrapper.api.LinksApi;
import ru.tinkoff.scrapper.controller.dto.request.AddLinkRequest;
import ru.tinkoff.scrapper.controller.dto.request.RemoveLinkRequest;
import ru.tinkoff.scrapper.controller.dto.response.LinkResponse;
import ru.tinkoff.scrapper.controller.dto.response.ListLinksResponse;
import ru.tinkoff.scrapper.domain.dto.Link;
import ru.tinkoff.scrapper.scheduler.LinkUpdaterScheduler;
import ru.tinkoff.scrapper.service.LinkService;

@RestController
@RequiredArgsConstructor
public class LinksController implements LinksApi {
    private static final Logger LOG = Logger.getLogger(LinkUpdaterScheduler.class.getName());
    private final LinkService service;

    @Override
    public ListLinksResponse linksGet(Long tgChatId) {
        LOG.log(Level.INFO, "links get request for chat " + tgChatId);
        List<LinkResponse> listResponse = new ArrayList<>();

        for (Link link : service.listAll(tgChatId)) {
            listResponse.add(new LinkResponse(link.getId(), URI.create(link.getLink())));
        }

        return new ListLinksResponse(listResponse, listResponse.size());
    }

    @Override
    public LinkResponse linksPost(Long tgChatId, AddLinkRequest body) {
        LOG.log(Level.INFO, "add link request link: " + body.link() + " to chat id: " + tgChatId);
        Link link = service.add(tgChatId, body.link());
        return new LinkResponse(link.getId(), URI.create(link.getLink()));
    }

    @Override
    public LinkResponse linksDelete(Long tgChatId, RemoveLinkRequest body) {
        LOG.log(Level.INFO, "remove link request " + body.link() + " from chat id: " + tgChatId);
        Link link = service.remove(tgChatId, body.link());
        return new LinkResponse(link.getId(), URI.create(link.getLink()));
    }
}
