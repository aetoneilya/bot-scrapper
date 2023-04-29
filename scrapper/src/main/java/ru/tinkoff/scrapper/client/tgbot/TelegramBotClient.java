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

@Service
public class TelegramBotClient {
    private final WebClient webClient;

    public TelegramBotClient(@Qualifier("tgBotClient") WebClient webClient) {
        this.webClient = webClient;
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
