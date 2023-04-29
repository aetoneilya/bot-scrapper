package ru.tinkoff.scrapper.service.jdbc;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.GitHubLink;
import ru.tinkoff.StackOverflowLink;
import ru.tinkoff.UrlParser;
import ru.tinkoff.scrapper.client.github.GitHubClient;
import ru.tinkoff.scrapper.client.stackoverflow.StackOverflowClient;
import ru.tinkoff.scrapper.domain.dto.Chat;
import ru.tinkoff.scrapper.domain.dto.Link;
import ru.tinkoff.scrapper.domain.jdbc.JdbcLinkRepository;
import ru.tinkoff.scrapper.service.LinkService;

import java.net.URI;
import java.sql.Timestamp;
import java.util.List;

//@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {

    private final JdbcLinkRepository repository;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final UrlParser urlParser;
    private final Gson gson;

    @Override
    @Transactional
    public Link add(long tgChatId, URI url) {
        Link link = new Link();
        link.setLink(url.toString());
        link.setLastUpdate(new Timestamp(System.currentTimeMillis()));

        switch (urlParser.parseUri(url)) {
            case GitHubLink g -> link.setState(gson.toJson(gitHubClient.getLastCommit(g.user(), g.repository())));
            case StackOverflowLink s ->
                    link.setState(gson.toJson(stackOverflowClient.getStackOverflowResponse(s.id())));
            case null -> throw new RuntimeException("Unsupported link");
        }

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