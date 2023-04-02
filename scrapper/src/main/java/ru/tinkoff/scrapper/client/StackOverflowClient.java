package ru.tinkoff.scrapper.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.scrapper.dto.response.StackOverflowResponse;

@Service
public class StackOverflowClient {
    private final WebClient webClient;

    public StackOverflowClient(@Qualifier("stackoverflowClient")WebClient webClient ){
        this.webClient = webClient;
    }

    public StackOverflowResponse getStackOverflowResponse(String questionId) {
        return webClient.get()
                .uri("/questions/{id}?site=stackoverflow", questionId)
                .retrieve()
                .bodyToMono(StackOverflowResponse.class)
                .block();
    }
}
