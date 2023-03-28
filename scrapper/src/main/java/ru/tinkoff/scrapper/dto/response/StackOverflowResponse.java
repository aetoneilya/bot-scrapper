package ru.tinkoff.scrapper.dto.response;

import java.time.OffsetDateTime;

public record StackOverflowResponse(OffsetDateTime activity, OffsetDateTime creation, long votes) {
}
