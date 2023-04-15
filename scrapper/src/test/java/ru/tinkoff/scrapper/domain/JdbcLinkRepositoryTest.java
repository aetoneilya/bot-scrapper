package ru.tinkoff.scrapper.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.scrapper.IntegrationEnvironment;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JdbcLinkRepositoryTest  extends IntegrationEnvironment {
    @Autowired
    private JdbcLinkRepository linkRepository;
    @Autowired
    private JdbcTgChatRepository chatRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    @Rollback
    void addTest() {
    }

    @Transactional
    @Rollback
    void removeTest() {
    }

    @Transactional
    @Rollback
    void findAllTest() {
    }
}