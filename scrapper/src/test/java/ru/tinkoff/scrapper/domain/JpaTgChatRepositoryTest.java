package ru.tinkoff.scrapper.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.scrapper.IntegrationEnvironment;
import ru.tinkoff.scrapper.domain.dto.Chat;
import ru.tinkoff.scrapper.domain.jpa.JpaTgChatRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class JpaTgChatRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private JpaTgChatRepository chatRepository;


    @Test
    @Transactional
    @Rollback
    public void addFindTest() {
        List<Chat> chats = new ArrayList<>();
        chats.add(new Chat(1L, new ArrayList<>()));
        chats.add(new Chat(2L, new ArrayList<>()));
        chats.add(new Chat(3L, new ArrayList<>()));

        chatRepository.saveAll(chats);

        List<Chat> bdChats = chatRepository.findAll();
        Assertions.assertEquals(chats.size(), bdChats.size());
        for (int i = 0; i < chats.size(); i++) {
            Assertions.assertEquals(chats.get(i).getLinks(), bdChats.get(i).getLinks());
        }
        
    }

    @Test
    @Transactional
    @Rollback
    public void addRemoveFindTest() {
        List<Chat> chats = new ArrayList<>();
        chats.add(new Chat(1L, new ArrayList<>()));
        chats.add(new Chat(2L, new ArrayList<>()));
        chats.add(new Chat(3L, new ArrayList<>()));

        chatRepository.saveAll(chats);
        chatRepository.delete(chats.get(0));
        chats.remove(0);

        Assertions.assertEquals(chatRepository.findAll().size(), chats.size());
    }
}
