package ru.tinkoff.scrapper.service;

public interface TgChatService {
    void register(long tgChatId);

    void unregister(long tgChatId);
}
