package ru.tinkoff.bot.client.scrapper;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.bot.client.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.bot.client.scrapper.dto.request.RemoveLinkRequest;
import ru.tinkoff.bot.client.scrapper.dto.response.LinkResponse;
import ru.tinkoff.bot.client.scrapper.dto.response.ListLinksResponse;

public class ScrapperClient {
    private final WebClient webClient;

    public ScrapperClient(@NotNull String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public void addChat(Long id) {
        webClient.post().uri("/tg-chat/{id}", id).retrieve().bodyToMono(Void.class).block();
    }

    public void deleteChat(Long id) {
        webClient.delete().uri("/tg-chat/{id}", id).retrieve().bodyToMono(Void.class).block();
    }

    public ListLinksResponse getAllLinks(long id) {
        return webClient.get()
                .uri("/links")
                .header("Tg-Chat-Id", String.valueOf(id))
                .retrieve()
                .bodyToMono(ListLinksResponse.class)
                .block();
    }

    public LinkResponse removeLink(Long id, String removeLink) {
        return webClient.method(HttpMethod.DELETE)
                .uri("/links", id)
                .header("Tg-Chat-Id", String.valueOf(id))
                .body(BodyInserters.fromValue(new RemoveLinkRequest(removeLink)))
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .block();
    }

    public LinkResponse addLink(long id, String url) {
        return webClient.post()
                .uri("/links", id)
                .header("Tg-Chat-Id", String.valueOf(id))
                .body(BodyInserters.fromValue(new AddLinkRequest(url)))
                .retrieve()
                .bodyToMono(LinkResponse.class)
                .block();
    }

}
