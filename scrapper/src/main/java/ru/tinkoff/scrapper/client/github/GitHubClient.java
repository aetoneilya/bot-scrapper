package ru.tinkoff.scrapper.client.github;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.scrapper.client.github.dto.response.GitHubCommit;
import ru.tinkoff.scrapper.client.github.dto.response.GitHubResponse;

@Service
public class GitHubClient {
    private final WebClient webClient;

    public GitHubClient(@Qualifier("githubClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public GitHubResponse getGitHubResponse(String username, String repositoryName) {
        return webClient.get()
                .uri("/repos/{username}/{repositoryName}", username, repositoryName)
                .retrieve()
                .bodyToMono(GitHubResponse.class)
                .block();
    }

    public GitHubCommit getLastCommit(String username, String repositoryName) {
        return webClient.get()
                .uri("/repos/{username}/{repositoryName}/commits/main", username, repositoryName)
                .retrieve()
                .bodyToMono(GitHubCommit.class)
                .block();
    }
}
