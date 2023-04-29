package ru.tinkoff.scrapper.controller.dto.response;

import java.net.URI;

public record LinkResponse(long id, URI url) {
}