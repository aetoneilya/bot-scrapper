package ru.tinkoff.scrapper.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record StackOverflowItem(@JsonProperty("creation_date") OffsetDateTime creationDate,
                                @JsonProperty("last_activity_date") OffsetDateTime lastActivityDate) {
}
