package ru.tinkoff;

public record GitHubResponse(String user, String repository) implements UrlParserResponse {
}
