package ru.tinkoff.scrapper.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.scrapper.IntegrationEnvironment;
import ru.tinkoff.scrapper.domain.dto.Link;
import ru.tinkoff.scrapper.domain.jooq.JooqLinkRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class JooqLinkRepositoryTest extends IntegrationEnvironment {
    @Autowired
    private JooqLinkRepository linkRepository;

    @Test
    @Transactional
    @Rollback
    public void insertFindTest() {
        List<Link> links = new ArrayList<>();
        links.add(new Link(null, "stackoferflow.com", Timestamp.valueOf("2001-12-12 12:12:00"), "{\"answerCount\":29}", new ArrayList<>()));
        links.add(new Link(null, "github.com", Timestamp.valueOf("2001-12-12 12:12:00"), "{\"answerCount\":29}", new ArrayList<>()));

        for (Link l : links) {
            linkRepository.add(l);
        }

        List<Link> bdLinks = linkRepository.findAll();
        Assertions.assertEquals(links.size(), bdLinks.size());
        for (int i = 0; i < links.size(); i++) {
            Assertions.assertEquals(links.get(i).getLink(), bdLinks.get(i).getLink());
            Assertions.assertEquals(links.get(i).getState(), bdLinks.get(i).getState());
            Assertions.assertEquals(links.get(i).getChats(), bdLinks.get(i).getChats());
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

        for (Link l : links) {
            linkRepository.add(l);
        }

        Link result = linkRepository.getByUrl("target.com");

        Assertions.assertNotNull(result);
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

        for (Link l : links) {
            linkRepository.add(l);
        }

        Link result = linkRepository.getByUrl("random.com");

        Assertions.assertNull(result);
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

        for (Link l : links) {
            linkRepository.add(l);
        }

        List<Link> result = linkRepository.findOlderThan(30);

        Assertions.assertEquals(links.size(), result.size());
    }

}