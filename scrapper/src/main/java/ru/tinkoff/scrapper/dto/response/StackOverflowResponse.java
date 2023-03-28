package ru.tinkoff.scrapper.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public record StackOverflowResponse(@JsonProperty("last_activity_date") OffsetDateTime creationDate) {
}
