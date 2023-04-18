package ru.tinkoff.scrapper.service.jdbc;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tinkoff.GitHubLink;
import ru.tinkoff.StackOverflowLink;
import ru.tinkoff.UrlParser;
import ru.tinkoff.scrapper.client.github.GitHubClient;
import ru.tinkoff.scrapper.client.stackoverflow.StackOverflowClient;
import ru.tinkoff.scrapper.client.tgbot.TelegramBotClient;
import ru.tinkoff.scrapper.client.tgbot.dto.request.UpdatesRequest;
import ru.tinkoff.scrapper.domain.LinkRepository;
import ru.tinkoff.scrapper.domain.dto.Link;
import ru.tinkoff.scrapper.service.LinkUpdater;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JdbcLinkUpdater implements LinkUpdater {

    private final LinkRepository linkRepository;
    private final GitHubClient gitHubClient;
    private final TelegramBotClient telegramBotClient;
    private final StackOverflowClient stackOverflowClient;
    private final UrlParser urlParser;
    private final Gson gson;
    @Value("${scrapper.update-frequency}")
    int updateFrequency;


    @Override
    public int update() {
        List<Link> oldLinks = linkRepository.findOlderThan(updateFrequency);

        for (Link link : oldLinks) {
            updateLink(link);
        }

        return 0;
    }

    private void updateLink(Link link) {
        String newState = link.getJsonState();
        switch (urlParser.parse(link.getLink())) {
            case GitHubLink g -> newState = gson.toJson(gitHubClient.getLastCommit(g.user(), g.repository()));
            case StackOverflowLink s -> newState = gson.toJson(stackOverflowClient.getStackOverflowResponse(s.id()));
        }

        if (!link.getJsonState().equals(newState)) {
            sendUpdateToBot(link, link.getJsonState() + " -> " + newState);
        }
        saveNewState(link, newState);
    }

    private void sendUpdateToBot(Link link, String description) {
        UpdatesRequest request = new UpdatesRequest(link.getId(), link.getLink(), description, List.of(link.getChat().getId()));
        telegramBotClient.update(request);
    }

    private void saveNewState(Link link, String newState) {
        link.setJsonState(newState);
        link.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        linkRepository.update(link);
    }
}
