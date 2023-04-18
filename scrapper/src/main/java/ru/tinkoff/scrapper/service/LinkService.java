package ru.tinkoff.scrapper.service;

import ru.tinkoff.scrapper.domain.dto.Link;

import java.net.URI;
import java.util.List;

public interface LinkService {
    Link add(long tgChatId, URI url);
    Link remove(long tgChatId, URI url);
    List<Link> listAll(long tgChatId);
}