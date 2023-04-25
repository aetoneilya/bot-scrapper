package ru.tinkoff.scrapper.domain.jooq;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import ru.tinkoff.scrapper.domain.dto.Chat;
import ru.tinkoff.scrapper.domain.dto.Link;

import java.util.List;

import static ru.tinkoff.scrapper.domain.jooq.Tables.CHATS_TO_LINKS;
import static ru.tinkoff.scrapper.domain.jooq.tables.Links.LINKS;


@RequiredArgsConstructor
public class JooqLinkRepository {
    private final DSLContext dsl;
    private final RecordMapper<Record, Link> recordMapper = new RecordMapper<>() {
        @Override
        public @Nullable Link map(Record record) {
            return null;
        }
    };

    public Link add(Link link) {
        return dsl.insertInto(LINKS)
                .set(LINKS.LINK, link.getLink())
                .set(LINKS.LAST_UPDATE, link.getLastUpdate().toLocalDateTime())
                .set(LINKS.STATE, link.getState())
                .onConflictDoNothing().returning(LINKS.ID).fetchOne().map(recordMapper);
    }

    public Link getByUrl(String url) {
        return dsl.select(LINKS).where(LINKS.LINK.eq(url)).fetchOne().map(recordMapper);
    }

    public int update(Link link) {
        return dsl.update(LINKS)
                .set(LINKS.LINK, link.getLink())
                .set(LINKS.LAST_UPDATE, link.getLastUpdate().toLocalDateTime())
                .set(LINKS.STATE, link.getState())
                .where(LINKS.ID.eq((int) link.getId())).execute();
    }

    public int remove(Chat chat, Link link) {
        return dsl.delete(CHATS_TO_LINKS)
                .where(CHATS_TO_LINKS.LINK_ID.eq((int) link.getId()))
                .and(CHATS_TO_LINKS.CHAT_ID.eq((int) chat.getId())).execute();
    }

    public List<Link> findAllByChat(Chat chat) {
        return jdbcTemplate.query(
                "select * from chats_to_links" +
                        " join links l on l.id = chats_to_links.link_id where chat_id = :id",
                new BeanPropertySqlParameterSource(chat),
                rowMapper);
    }

    public List<Link> findAll() {
        return dsl.select(LINKS).fetch().map(recordMapper);
    }

    public List<Link> findOlderThan(int minutes) {
        return jdbcTemplate.query("select *, now() - last_update from links where (now() - last_update) > (:interval * interval '1 minute')",
                new MapSqlParameterSource()
                        .addValue("interval", minutes),
                rowMapper);
    }

    public int addLinkToChat(Chat chat, Link link) {
        return dsl.insertInto(CHATS_TO_LINKS)
                .set(CHATS_TO_LINKS.CHAT_ID, (int) chat.getId())
                .set(CHATS_TO_LINKS.LINK_ID, (int) link.getId()).onConflictDoNothing()
                .execute();
    }

    public List<Integer> getChatIds(Link link) {
        return dsl.select(CHATS_TO_LINKS).where(CHATS_TO_LINKS.LINK_ID.eq((int) link.getId())).fetch(CHATS_TO_LINKS.CHAT_ID);
    }
}
