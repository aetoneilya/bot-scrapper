package ru.tinkoff.scrapper.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record StackOverflowResponse(@JsonProperty("items") List<StackOverflowItem> items) {
}

