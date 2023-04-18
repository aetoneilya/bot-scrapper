package ru.tinkoff.scrapper.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;

@Validated
@Configuration
public class ClientConfiguration {
    @Value("${clients.base-url.github:https://api.github.com}")
    private String gitHubBaseUrl;
    @Value("${clients.base-url.stackoverflow:https://api.stackexchange.com/2.3}")
    private String stackOverflowBaseUrl;
    @Value("${clients.base-url.bot:http://localhost:8080}")
    private String botBaseUrl;

    @Bean("githubClient")
    public WebClient getGitHubClient() {
        return WebClient.builder().baseUrl(gitHubBaseUrl).build();
    }

    @Bean("stackoverflowClient")
    public WebClient getStackOverflowClient() {
        return WebClient.builder().baseUrl(stackOverflowBaseUrl).build();
    }

    @Bean("tgBotClient")
    public WebClient getBotClient() {
        return WebClient.builder().baseUrl(botBaseUrl).build();
    }
}
