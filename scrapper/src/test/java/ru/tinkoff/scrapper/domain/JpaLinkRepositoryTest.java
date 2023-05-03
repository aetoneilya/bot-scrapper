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
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class JpaLinkRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private JpaLinkRepository linkRepository;

    @Test
    @Transactional
    @Rollback
    public void insertFindTest() {
        List<Link> links = new ArrayList<>();
        links.add(new Link(1L, "stackoferflow.com", Timestamp.valueOf("2001-12-12 12:12:00"), "{\"answerCount\":29}", new ArrayList<>()));

        linkRepository.saveAll(links);

        List<Link> bdLinks = linkRepository.findAll();
        Assertions.assertEquals(links.size(), bdLinks.size());
        for (int i = 0; i < links.size(); i++) {
            Assertions.assertEquals(links.get(i), bdLinks.get(i));
        }
    }

    @Test
    @Transactional
    @Rollback
    public void findByLinkPresentTest() {
        List<Link> links = new ArrayList<>();
        links.add(new Link(1L, "stackoferflow.com", Timestamp.valueOf("2001-12-12 12:12:00"), "{\"answerCount\":29}", new ArrayList<>()));
        links.add(new Link(2L, "github.com", Timestamp.valueOf("2001-12-12 12:12:00"), "{\"answerCount\":29}", new ArrayList<>()));
        links.add(new Link(3L, "randomlink.com", Timestamp.valueOf("2001-12-12 12:12:00"), "{\"answerCount\":29}", new ArrayList<>()));
        links.add(new Link(4L, "target.com", Timestamp.valueOf("2001-12-12 12:12:00"), "{\"answerCount\":29}", new ArrayList<>()));
        linkRepository.saveAll(links);

        Optional<Link> result = linkRepository.findByLink("target.com");

        Assertions.assertTrue(result.isPresent());
    }

    @Test
    @Transactional
    @Rollback
    public void findByLinkNotPresentTest() {
        List<Link> links = new ArrayList<>();
        links.add(new Link(1L, "stackoferflow.com", Timestamp.valueOf("2001-12-12 12:12:00"), "{\"answerCount\":29}", new ArrayList<>()));
        links.add(new Link(2L, "github.com", Timestamp.valueOf("2001-12-12 12:12:00"), "{\"answerCount\":29}", new ArrayList<>()));
        links.add(new Link(3L, "randomlink.com", Timestamp.valueOf("2001-12-12 12:12:00"), "{\"answerCount\":29}", new ArrayList<>()));
        links.add(new Link(4L, "target.com", Timestamp.valueOf("2001-12-12 12:12:00"), "{\"answerCount\":29}", new ArrayList<>()));
        linkRepository.saveAll(links);

        Optional<Link> result = linkRepository.findByLink("random.com");

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    @Transactional
    @Rollback
    public void findOlderThanTest() {
        List<Link> links = new ArrayList<>();
        links.add(new Link(1L, "stackoferflow.com", Timestamp.valueOf("2001-12-12 12:12:00"), "{\"answerCount\":29}", new ArrayList<>()));
        links.add(new Link(2L, "github.com", Timestamp.valueOf("2001-12-12 12:12:00"), "{\"answerCount\":29}", new ArrayList<>()));
        links.add(new Link(3L, "randomlink.com", Timestamp.valueOf("2001-12-12 12:12:00"), "{\"answerCount\":29}", new ArrayList<>()));
        links.add(new Link(4L, "target.com", Timestamp.valueOf("2001-12-12 12:12:00"), "{\"answerCount\":29}", new ArrayList<>()));
        linkRepository.saveAll(links);

        List<Link> result = linkRepository.findOlderThan(Duration.ofMinutes(30));

        Assertions.assertEquals(links.size(), result.size());
    }
}
