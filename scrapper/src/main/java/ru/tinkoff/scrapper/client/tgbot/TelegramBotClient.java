package ru.tinkoff.scrapper.client.tgbot;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.scrapper.client.tgbot.dto.request.UpdatesRequest;
import ru.tinkoff.scrapper.client.tgbot.dto.response.ApiErrorResponse;
import ru.tinkoff.scrapper.client.tgbot.exception.TgBotClientException;
import ru.tinkoff.scrapper.domain.dto.Chat;
import ru.tinkoff.scrapper.domain.dto.Link;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TelegramBotClient {
    private final WebClient webClient;

    public TelegramBotClient(@Qualifier("tgBotClient") WebClient webClient) {
        this.webClient = webClient;
    }


    public void sendUpdateToBot(Link link, String description) {
        List<Long> chats = link.getChats().stream().map(Chat::getId).collect(Collectors.toList());
        UpdatesRequest request = new UpdatesRequest(link.getId(), link.getLink(), description, chats);
        update(request);
    }


    public void update(UpdatesRequest updatesRequest) {
        webClient.post().uri("/updates")
                .body(BodyInserters.fromValue(updatesRequest))
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(ApiErrorResponse.class)
                        .flatMap(error -> Mono.error(new TgBotClientException(error.exceptionMessage()))))
                .bodyToMono(Void.class)
                .block();
    }
}
