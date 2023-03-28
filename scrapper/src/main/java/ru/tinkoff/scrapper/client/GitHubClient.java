package ru.tinkoff.scrapper.client;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.scrapper.dto.response.GitHubResponse;

public class GitHubClient {
    private static final String baseUrl = "https://api.github.com";
    private final WebClient webClient;

    public GitHubClient(String baseUrl ){
        this.webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public GitHubClient(){
        this(baseUrl);
    }

    public GitHubResponse getGitHubResponse(String username, String repositoryName) {
        return webClient.get()
                .uri("/repos/{username}/{repositoryName}/hooks", username, repositoryName)
                .retrieve()
                .bodyToMono(GitHubResponse.class)
                .block();
    }
}
