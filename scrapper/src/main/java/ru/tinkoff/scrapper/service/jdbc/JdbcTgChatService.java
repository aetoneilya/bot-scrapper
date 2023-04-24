package ru.tinkoff.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.scrapper.domain.jdbc.JdbcTgChatRepository;
import ru.tinkoff.scrapper.domain.dto.Chat;
import ru.tinkoff.scrapper.service.TgChatService;

//@Service
@RequiredArgsConstructor
public class JdbcTgChatService implements TgChatService {

    private final JdbcTgChatRepository repository;

    @Override
    public void register(long tgChatId) {
        repository.add(new Chat(tgChatId, null));
    }

    @Override
    public void unregister(long tgChatId) {
        repository.remove(new Chat(tgChatId, null));
    }
}
