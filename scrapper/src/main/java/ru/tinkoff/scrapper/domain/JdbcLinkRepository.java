package ru.tinkoff.scrapper.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.scrapper.domain.dto.Link;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcLinkRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Link> rowMapper = new DataClassRowMapper<>(Link.class);

    public void add(Link link) {
        jdbcTemplate.update("insert into links(link, chat_id) values (:link, :chatId)", new MapSqlParameterSource()
                .addValue("link", link.getLink())
                .addValue("chatId", link.getChat().getId()));
    }

    public void remove(Link link) {
        jdbcTemplate.update("delete from links where id = :id", new BeanPropertySqlParameterSource(link));
    }

    public List<Link> findAll() {
        return jdbcTemplate.query("select * from links", rowMapper);
    }
}
