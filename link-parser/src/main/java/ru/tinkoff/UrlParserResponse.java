package ru.tinkoff;

public sealed interface UrlParserResponse permits GitHubResponse, StackOverflowResponse {
}
