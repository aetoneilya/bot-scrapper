package ru.tinkoff.scrapper.dto.response;

import java.util.List;

public record ListLinksResponse(List<LinkResponse> links, int size) {

}
