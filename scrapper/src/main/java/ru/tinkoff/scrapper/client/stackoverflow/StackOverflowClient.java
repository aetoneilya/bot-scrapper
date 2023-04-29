package ru.tinkoff.scrapper.client.stackoverflow;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.scrapper.client.stackoverflow.dto.response.StackOverflowQuestion;
import ru.tinkoff.scrapper.client.stackoverflow.dto.response.StackOverflowResponse;

@Service
public class StackOverflowClient {
    private final WebClient webClient;

    public StackOverflowClient(@Qualifier("stackoverflowClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public StackOverflowQuestion getStackOverflowResponse(Long questionId) {
        return getStackOverflowResponse(questionId.toString());
    }

    public StackOverflowQuestion getStackOverflowResponse(String questionId) {
        StackOverflowResponse response = webClient.get()
                .uri("/questions/{id}?site=stackoverflow", questionId)
                .retrieve()
                .bodyToMono(StackOverflowResponse.class)
                .block();
        return response == null || response.items().size() == 0 ? null : response.items().get(0);
    }
}
