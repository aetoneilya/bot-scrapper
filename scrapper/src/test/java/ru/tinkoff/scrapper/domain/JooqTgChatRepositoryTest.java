package ru.tinkoff.scrapper.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.scrapper.IntegrationEnvironment;
import ru.tinkoff.scrapper.domain.dto.Chat;
import ru.tinkoff.scrapper.domain.jooq.JooqTgChatRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class JooqTgChatRepositoryTest extends IntegrationEnvironment {

    @Autowired
    private JooqTgChatRepository chatRepository;

    @Test
    @Transactional
    @Rollback
    public void addFindTest() {
        List<Chat> chats = new ArrayList<>();
        chats.add(new Chat(1, new ArrayList<>()));
        chats.add(new Chat(2, new ArrayList<>()));
        chats.add(new Chat(3, new ArrayList<>()));

        for (Chat chat : chats) {
            chatRepository.add(chat);
        }

        Assertions.assertIterableEquals(chatRepository.findAll(), chats);
    }

    @Test
    @Transactional
    @Rollback
    public void addRemoveFindTest() {
        List<Chat> chats = new ArrayList<>();
        chats.add(new Chat(1, new ArrayList<>()));
        chats.add(new Chat(2, new ArrayList<>()));
        chats.add(new Chat(3, new ArrayList<>()));

        for (Chat chat : chats) {
            chatRepository.add(chat);
        }
        chatRepository.remove(chats.get(0));
        chats.remove(0);

        Assertions.assertIterableEquals(chatRepository.findAll(), chats);
    }
}