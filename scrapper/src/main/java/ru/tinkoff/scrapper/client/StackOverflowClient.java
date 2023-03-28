package ru.tinkoff.scrapper.client;

import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.scrapper.dto.response.StackOverflowResponse;

public class StackOverflowClient {
    private static final String baseUrl = "https://api.stackexchange.com/2.3";
    private final WebClient webClient;

    public StackOverflowClient(String baseUrl ){
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public StackOverflowClient(){
        this(baseUrl);
    }

    public StackOverflowResponse getStackOverflowResponse(String questionId) {
        return webClient.get()
                .uri("/questions/{id}?site=stackoverflow", questionId)
                .retrieve()
                .bodyToMono(StackOverflowResponse.class)
                .block();
    }
}
