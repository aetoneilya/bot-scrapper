package ru.tinkoff;

public record GitHubLink(String user, String repository) implements UrlParserResponse {
}
