package ru.tinkoff.scrapper.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.scrapper.domain.dto.Chat;

import java.util.List;

//@Repository
@RequiredArgsConstructor
public class JdbcTgChatRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Chat> rowMapper = new DataClassRowMapper<>(Chat.class);

    public void add(Chat chat) {
        jdbcTemplate.update("insert into chats(id) values (:id)", new BeanPropertySqlParameterSource(chat));
    }

    public void remove(Chat chat) {
        jdbcTemplate.update("delete from chats where id = :id", new BeanPropertySqlParameterSource(chat));
    }

    public List<Chat> findAll() {
        return jdbcTemplate.query("select * from chats", rowMapper);
    }
}
