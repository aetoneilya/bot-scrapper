package ru.tinkoff.scrapper.service;

import java.net.URI;
import java.util.List;
import ru.tinkoff.scrapper.domain.dto.Link;

public interface LinkService {
    Link add(long tgChatId, URI url);

    Link remove(long tgChatId, URI url);

    List<Link> listAll(long tgChatId);
}
