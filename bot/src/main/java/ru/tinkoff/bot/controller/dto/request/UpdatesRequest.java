package ru.tinkoff.bot.controller.dto.request;

import java.util.List;

public record UpdatesRequest(long id, String url, String description, List<Long> tgChatIds) {
}
