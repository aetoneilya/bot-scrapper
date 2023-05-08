package ru.tinkoff.scrapper.client.github.dto.response;

public record GitHubCommit(String sha, Commit commit, Author author) {
}

