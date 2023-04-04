package ru.tinkoff.scrapper.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record GitHubResponse(long id, @JsonProperty("updated_at") OffsetDateTime updatedAt, @JsonProperty("pushed_at") OffsetDateTime createdAt) {
}
