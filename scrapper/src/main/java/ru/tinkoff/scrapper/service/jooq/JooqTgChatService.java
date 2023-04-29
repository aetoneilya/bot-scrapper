package ru.tinkoff.scrapper.service.jooq;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.scrapper.domain.dto.Chat;
import ru.tinkoff.scrapper.domain.jooq.JooqTgChatRepository;
import ru.tinkoff.scrapper.service.TgChatService;

@Service
@RequiredArgsConstructor
public class JooqTgChatService implements TgChatService {
    private final JooqTgChatRepository repository;

    @Override
    public void register(long tgChatId) {
        repository.add(new Chat(tgChatId, null));
    }

    @Override
    public void unregister(long tgChatId) {
        repository.remove(new Chat(tgChatId, null));
    }
}
