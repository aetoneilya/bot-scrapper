package ru.tinkoff.scrapper;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.scrapper.repository.dto.Chat;
import ru.tinkoff.scrapper.repository.dto.Link;

import java.util.ArrayList;
import java.util.List;

public class TestContainerInitializationTest extends IntegrationEnvironment {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public TestContainerInitializationTest() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(DB_CONTAINER.getJdbcUrl());
        dataSource.setUsername(DB_CONTAINER.getUsername());
        dataSource.setPassword(DB_CONTAINER.getPassword());

        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Test
    @Transactional
    @Rollback
    public void chatTableTest() {
        List<Chat> chats = new ArrayList<>();
        chats.add(new Chat(1));
        chats.add(new Chat(423));
        chats.add(new Chat(777));

        for (Chat c : chats) {
            addChat(c);
        }

        Assertions.assertIterableEquals(chats, getChats());
    }

    @Test
    @Transactional
    @Rollback
    public void linkTableTest() {
        Chat user1 = new Chat(9999);
        Chat user2 = new Chat(34234);
        List<Link> links = new ArrayList<>();
        links.add(new Link(1, "stackoferflow.com", user1));
        links.add(new Link(1, "github.com", user1));
        links.add(new Link(1, "stackoferflow.com", user2));

        addChat(user1);
        addChat(user2);
        for (Link l : links){
            addLink(l);
        }

        List<Link> bdLinks = getLinks();
        Assertions.assertEquals(links.size(), bdLinks.size());
        for (int i = 0; i < links.size(); i++) {
            Assertions.assertEquals(links.get(i).getLink(), bdLinks.get(i).getLink());
        }
    }


    public int addChat(Chat chat) {
        String inQuery = "INSERT INTO chats(id) VALUES (:id)";
        return jdbcTemplate.update(inQuery, new MapSqlParameterSource()
                .addValue("id", chat.getId()));
    }

    public List<Chat> getChats() {
        String alQuery = "SELECT * FROM chats";
        List<Chat> chats = jdbcTemplate.query(alQuery, new BeanPropertyRowMapper<>(Chat.class));
        return chats.isEmpty() ? null : chats;
    }

    public int addLink(Link link) {
        String inQuery = "INSERT INTO links(link, chat_id) VALUES (:link, :chatId)";
        return jdbcTemplate.update(inQuery, new MapSqlParameterSource()
                .addValue("link", link.getLink())
                .addValue("chatId", link.getChat().getId()));
    }

    public List<Link> getLinks() {
        String alQuery = "SELECT * FROM links";
        List<Link> links = jdbcTemplate.query(alQuery, new BeanPropertyRowMapper<>(Link.class));
        return links.isEmpty() ? null : links;
    }
}
