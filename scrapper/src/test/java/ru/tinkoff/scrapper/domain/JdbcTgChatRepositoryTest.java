package ru.tinkoff.scrapper.domain;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.scrapper.IntegrationEnvironment;
import ru.tinkoff.scrapper.domain.dto.Chat;
import ru.tinkoff.scrapper.domain.jdbc.JdbcLinkRepository;
import ru.tinkoff.scrapper.domain.jdbc.JdbcTgChatRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class JdbcTgChatRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private JdbcLinkRepository linkRepository;
    @Autowired
    private JdbcTgChatRepository chatRepository;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    //TODO: need to autowire this
    public JdbcTgChatRepositoryTest() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(DB_CONTAINER.getJdbcUrl());
        dataSource.setUsername(DB_CONTAINER.getUsername());
        dataSource.setPassword(DB_CONTAINER.getPassword());

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        linkRepository = new JdbcLinkRepository(jdbcTemplate);
        chatRepository = new JdbcTgChatRepository(jdbcTemplate);
    }

    @Test
    @Transactional
    @Rollback
    public void addFindTest() {
        List<Chat> chats = new ArrayList<>();
        chats.add(new Chat(1));
        chats.add(new Chat(2));
        chats.add(new Chat(3));

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
        chats.add(new Chat(1));
        chats.add(new Chat(2));
        chats.add(new Chat(3));

        for (Chat chat : chats) {
            chatRepository.add(chat);
        }
        chatRepository.remove(chats.get(0));
        chats.remove(0);

        Assertions.assertIterableEquals(chatRepository.findAll(), chats);
    }
}