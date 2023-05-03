package ru.tinkoff.scrapper.service;

import com.google.gson.Gson;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.GitHubLink;
import ru.tinkoff.StackOverflowLink;
import ru.tinkoff.UrlParser;
import ru.tinkoff.scrapper.client.github.GitHubClient;
import ru.tinkoff.scrapper.client.stackoverflow.StackOverflowClient;
import ru.tinkoff.scrapper.client.tgbot.TelegramBotClient;
import ru.tinkoff.scrapper.client.tgbot.dto.request.UpdatesRequest;
import ru.tinkoff.scrapper.domain.dto.Chat;
import ru.tinkoff.scrapper.domain.dto.Link;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Utilities {
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final UrlParser urlParser;
    private final Gson gson;

    public Link createLink(String url) {
        Link link = new Link();
        link.setLink(url);
        link.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        link.setState(getNewState(link));

        return link;
    }

    public String getNewState(@NotNull Link link) {
        String newState = link.getState();
        switch (urlParser.parse(link.getLink())) {
            case GitHubLink g -> newState = gson.toJson(gitHubClient.getLastCommit(g.user(), g.repository()));
            case StackOverflowLink s -> newState = gson.toJson(stackOverflowClient.getStackOverflowResponse(s.id()));
        }
        return newState;
    }


}
