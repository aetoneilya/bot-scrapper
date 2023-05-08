package ru.tinkoff.scrapper.service.jdbc;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.scrapper.domain.dto.Chat;
import ru.tinkoff.scrapper.domain.dto.Link;
import ru.tinkoff.scrapper.domain.jdbc.JdbcLinkRepository;
import ru.tinkoff.scrapper.service.LinkService;
import ru.tinkoff.scrapper.service.Utilities;

@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {

    private final JdbcLinkRepository repository;
    private final Utilities utilities;

    @Override
    @Transactional
    public Link add(long tgChatId, URI url) {
        Link link = utilities.createLink(url.toString());

        link = repository.add(link);
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
