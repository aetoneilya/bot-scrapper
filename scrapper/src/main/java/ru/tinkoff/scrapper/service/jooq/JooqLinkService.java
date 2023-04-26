package ru.tinkoff.scrapper.service.jooq;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.scrapper.domain.dto.Chat;
import ru.tinkoff.scrapper.domain.dto.Link;
import ru.tinkoff.scrapper.domain.jooq.JooqLinkRepository;
import ru.tinkoff.scrapper.service.LinkService;
import ru.tinkoff.scrapper.service.Utilities;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JooqLinkService implements LinkService {
    private final JooqLinkRepository repository;
    private final Utilities utilities;
    @Override
    public Link add(long tgChatId, URI url) {
        Link link = utilities.createLink(url.toString());

        link.setId(repository.add(link));
        repository.addLinkToChat(new Chat(tgChatId, null), link);

        return link;
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        Link link = repository.getByUrl(url.toString());
        repository.remove(new Chat(tgChatId, null), link);
        return link;
    }

    @Override
    public List<Link> listAll(long tgChatId) {
        return repository.findAllByChat(new Chat(tgChatId, null));
    }
}
