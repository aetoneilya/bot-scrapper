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
import ru.tinkoff.scrapper.ScrapperApplication;
import ru.tinkoff.scrapper.domain.dto.Link;
import ru.tinkoff.scrapper.domain.jdbc.JdbcLinkRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = ScrapperApplication.class)
public class JdbcLinkRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private JdbcLinkRepository linkRepository;

    //TODO: need to autowire this
    public JdbcLinkRepositoryTest() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(DB_CONTAINER.getJdbcUrl());
        dataSource.setUsername(DB_CONTAINER.getUsername());
        dataSource.setPassword(DB_CONTAINER.getPassword());

        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        linkRepository = new JdbcLinkRepository(jdbcTemplate);
    }

    @Test
    @Transactional
    @Rollback
    public void insertFindTest() {
        List<Link> links = new ArrayList<>();
        links.add(new Link(1, "stackoferflow.com", Timestamp.valueOf("2001-12-12 12:12:00"), "{\"answerCount\":29}"));

        for (Link l : links) {
            linkRepository.add(l);
        }

        List<Link> bdLinks = linkRepository.findAll();
        Assertions.assertEquals(links.size(), bdLinks.size());
        for (int i = 0; i < links.size(); i++) {
            Assertions.assertEquals(links.get(i), bdLinks.get(i));
        }
    }

}