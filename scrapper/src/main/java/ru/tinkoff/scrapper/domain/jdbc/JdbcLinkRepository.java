package ru.tinkoff.scrapper.domain.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.scrapper.domain.dto.Chat;
import ru.tinkoff.scrapper.domain.dto.Link;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcLinkRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Link> rowMapper = new DataClassRowMapper<>(Link.class);

    public Link add(Link link) {
        return jdbcTemplate
                .queryForObject("insert into links(link, last_update, state) " +
                                "values (:link, :lastUpdate, :state::json) " +
                                "on conflict(link) do update set link = excluded.link returning *",
                        new BeanPropertySqlParameterSource(link),
                        rowMapper);
    }

    public Link getByUrl(String url) {
        return jdbcTemplate.queryForObject("select * from links where link = :link",
                new MapSqlParameterSource().addValue("link", url),
                rowMapper);
    }

    public int update(Link link) {
        return jdbcTemplate.update(
                "update links set link = :link, last_update = :lastUpdate, state = :state::json where id = :id",
                new BeanPropertySqlParameterSource(link));
    }

    public int remove(Chat chat, Link link) {
        return jdbcTemplate.update("delete from chats_to_links where link_id = :linkId and chat_id = :chatId",
                new MapSqlParameterSource()
                        .addValue("linkId", link.getId())
                        .addValue("chatId", chat.getId()));
    }

    public List<Link> findAllByChat(Chat chat) {
        return jdbcTemplate.query(
                "select * from chats_to_links" +
                        " join links l on l.id = chats_to_links.link_id where chat_id = :id",
                new BeanPropertySqlParameterSource(chat),
                rowMapper);
    }

    public List<Link> findAll() {
        return jdbcTemplate.query("select * from links", rowMapper);
    }

    public List<Link> findOlderThan(int minutes) {
        return jdbcTemplate.query("select *, now() - last_update from links where (now() - last_update) > (:interval * interval '1 minute')",
                new MapSqlParameterSource()
                        .addValue("interval", minutes),
                rowMapper);
    }

    public int addLinkToChat(Chat chat, Link link) {
        return jdbcTemplate.update("insert into chats_to_links(chat_id, link_id)" +
                        "values (:chatId, :linkId) on conflict(link_id, chat_id) do nothing",
                new MapSqlParameterSource()
                        .addValue("chatId", chat.getId())
                        .addValue("linkId", link.getId()));
    }

    public List<Long> getChatIds(Link link) {
        return jdbcTemplate.query("select chat_id from chats_to_links where link_id = :link_id",
                new MapSqlParameterSource().addValue("linkId", link.getId()), new DataClassRowMapper<>(Long.class));
    }
}