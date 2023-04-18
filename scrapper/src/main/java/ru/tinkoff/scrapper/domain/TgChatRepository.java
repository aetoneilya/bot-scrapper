package ru.tinkoff.scrapper.domain;

import ru.tinkoff.scrapper.domain.dto.Chat;

import java.util.List;

public interface TgChatRepository {
    Chat add(Chat chat);
    Chat remove(Chat chat);
    List<Chat> findAll();
}
