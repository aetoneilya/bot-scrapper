package ru.tinkoff.scrapper.controller.dto.response;

import java.util.List;

public record ListLinksResponse(
    List<LinkResponse> links,
    int size) {
}
