package ru.tinkoff.scrapper.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.scrapper.IntegrationEnvironment;
import ru.tinkoff.scrapper.domain.dto.Link;
import ru.tinkoff.scrapper.domain.jpa.JpaLinkRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class JpaLinkRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private JpaLinkRepository linkRepository;

    @Test
    @Transactional
    @Rollback
    public void insertFindTest() {
        List<Link> links = new ArrayList<>();
        links.add(new Link(1, "stackoferflow.com", Timestamp.valueOf("2001-12-12 12:12:00"), "{\"answerCount\":29}", new ArrayList<>()));

        linkRepository.saveAll(links);

        List<Link> bdLinks = linkRepository.findAll();
        Assertions.assertEquals(links.size(), bdLinks.size());
        for (int i = 0; i < links.size(); i++) {
            Assertions.assertEquals(links.get(i), bdLinks.get(i));
        }
    }
}
