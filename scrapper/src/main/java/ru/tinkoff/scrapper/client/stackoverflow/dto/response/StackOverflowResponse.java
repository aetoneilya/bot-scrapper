package ru.tinkoff.scrapper.client.stackoverflow.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record StackOverflowResponse(@JsonProperty("items") List<StackOverflowQuestion> items) {
}

