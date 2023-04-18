package ru.tinkoff.scrapper.domain.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.scrapper.domain.TgChatRepository;
import ru.tinkoff.scrapper.domain.dto.Chat;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcTgChatRepository implements TgChatRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Chat> rowMapper = new DataClassRowMapper<>(Chat.class);

    @Override
    public Chat add(Chat chat) {
        return jdbcTemplate.queryForObject("insert into chats(id) values (:id) returning *",
                new BeanPropertySqlParameterSource(chat),
                rowMapper);
    }

    @Override
    public Chat remove(Chat chat) {
        return jdbcTemplate.queryForObject("delete from chats where id = :id returning *",
                new BeanPropertySqlParameterSource(chat),
                rowMapper);
    }

    @Override
    public List<Chat> findAll() {
        return jdbcTemplate.query("select * from chats", rowMapper);
    }
}
