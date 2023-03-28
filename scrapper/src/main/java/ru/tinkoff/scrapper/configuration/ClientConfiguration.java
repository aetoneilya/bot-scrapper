package ru.tinkoff.scrapper.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.scrapper.client.GitHubClient;
import ru.tinkoff.scrapper.client.StackOverflowClient;

@Validated
@ConfigurationProperties(prefix = "scrapper", ignoreUnknownFields = false)
public class ClientConfiguration {
    @Bean
    GitHubClient getGitHubClient() {
        return new GitHubClient();
    }

    @Bean
    StackOverflowClient getStackOverflowClient() {
        return new StackOverflowClient();
    }
}
