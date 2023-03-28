package ru.tinkoff.scrapper.dto.response;

import java.time.OffsetDateTime;

public record GitHubResponse(long id, boolean active, OffsetDateTime updatedAt, OffsetDateTime createdAt) {
}
