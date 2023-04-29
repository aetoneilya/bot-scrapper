package ru.tinkoff.scrapper.client.stackoverflow.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StackOverflowQuestion(@JsonProperty("answer_count") int answerCount) {
}
