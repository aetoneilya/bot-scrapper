package ru.tinkoff.scrapper.domain.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.scrapper.domain.LinkRepository;
import ru.tinkoff.scrapper.domain.dto.Chat;
import ru.tinkoff.scrapper.domain.dto.Link;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcLinkRepository implements LinkRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<Link> rowMapper = (rs, rowNum) -> {
        Link link = new Link();
        link.setId(rs.getLong("id"));
        link.setLink(rs.getString("link"));
        link.setLastUpdate(rs.getTimestamp("last_update"));
        link.setJsonState(rs.getString("state"));

        Chat chat = new Chat(rs.getLong("chat_id"));

        link.setChat(chat);

        return link;
    };


    @Override
    public Link add(Link link) {
        return jdbcTemplate
                .queryForObject("insert into links(link, chat_id, last_update, state) " +
                                "values (:link, :chatId, :lastUpdate, :state::json) returning *",
                        new MapSqlParameterSource()
                                .addValue("link", link.getLink())
                                .addValue("chatId", link.getChat().getId())
                                .addValue("lastUpdate", link.getLastUpdate())
                                .addValue("state", link.getJsonState()),
                        rowMapper);
    }

    @Override
    public int update(Link link) {
        return jdbcTemplate.update("update links set link = :link, chat_id = :chatId, last_update = :lastUpdate, state = :state::json where id = :id", new MapSqlParameterSource()
                .addValue("link", link.getLink())
                .addValue("chatId", link.getChat().getId())
                .addValue("lastUpdate", link.getLastUpdate())
                .addValue("state", link.getJsonState())
                .addValue("id", link.getId()));
    }

    @Override
    public Link remove(Link link) {
        return jdbcTemplate
                .queryForObject("delete from links where link = :link and chat_id = :chatId returning *",
                        new MapSqlParameterSource()
                                .addValue("link", link.getLink())
                                .addValue("chatId", link.getChat().getId()),
                        rowMapper);
    }

    @Override
    public List<Link> findAll(Chat chat) {
        return jdbcTemplate.query("select * from links where chat_id = :id", new BeanPropertySqlParameterSource(chat), rowMapper);
    }

    @Override
    public List<Link> findOlderThan(int minutes) {
        return jdbcTemplate.query("select *, now() - last_update from links where (now() - last_update) > (20 * interval '1 minute')",
                new MapSqlParameterSource()
                        .addValue("timestamp", minutes),
                rowMapper);
    }
}