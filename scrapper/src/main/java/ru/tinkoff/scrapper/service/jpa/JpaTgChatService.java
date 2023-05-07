package ru.tinkoff.scrapper.service.jpa;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.scrapper.domain.dto.Chat;
import ru.tinkoff.scrapper.domain.jpa.JpaTgChatRepository;
import ru.tinkoff.scrapper.service.TgChatService;

@RequiredArgsConstructor
public class JpaTgChatService implements TgChatService {

    private final JpaTgChatRepository tgChatRepository;

    @Override
    public void register(long tgChatId) {
        Chat chats = new Chat();
        chats.setId(tgChatId);
        tgChatRepository.save(chats);
    }

    @Override
    public void unregister(long tgChatId) {
        Chat chats = new Chat();
        chats.setId(tgChatId);
        tgChatRepository.delete(chats);
    }
}
