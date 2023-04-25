package ru.tinkoff.scrapper.domain.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.JSON;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Repository;
import ru.tinkoff.scrapper.domain.dto.Chat;
import ru.tinkoff.scrapper.domain.dto.Link;
import ru.tinkoff.scrapper.domain.jooq.tables.records.LinksRecord;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.jooq.impl.DSL.currentTimestamp;
import static org.jooq.impl.DSL.val;
import static ru.tinkoff.scrapper.domain.jooq.Tables.CHATS_TO_LINKS;
import static ru.tinkoff.scrapper.domain.jooq.tables.Links.LINKS;


@Repository
@RequiredArgsConstructor
public class JooqLinkRepository {
    private final DSLContext dsl;
    private final RecordMapper<LinksRecord, Link> recordMapper = record -> new Link(
            record.getId(),
            record.getLink(),
            Timestamp.valueOf(record.getLastUpdate()),
            record.getState().toString(),
            new ArrayList<>());


    public Link add(Link link) {
        return dsl.insertInto(LINKS)
                .set(LINKS.LINK, link.getLink())
                .set(LINKS.LAST_UPDATE, link.getLastUpdate().toLocalDateTime())
                .set(LINKS.STATE, JSON.json(link.getState()))
                .onConflictDoNothing().returning(LINKS.ID).fetchOne()
                .map(record -> recordMapper.map((LinksRecord) record));
    }

    public Link getByUrl(String url) {
        return dsl.select(LINKS).where(LINKS.LINK.eq(url)).fetchOne()
                .map(record -> recordMapper.map((LinksRecord) record));
    }

    public int update(Link link) {
        return dsl.update(LINKS)
                .set(LINKS.LINK, link.getLink())
                .set(LINKS.LAST_UPDATE, link.getLastUpdate().toLocalDateTime())
                .set(LINKS.STATE, JSON.json(link.getState()))
                .where(LINKS.ID.eq((int) link.getId())).execute();
    }

    public int remove(Chat chat, Link link) {
        return dsl.delete(CHATS_TO_LINKS)
                .where(CHATS_TO_LINKS.LINK_ID.eq((int) link.getId()))
                .and(CHATS_TO_LINKS.CHAT_ID.eq((int) chat.getId())).execute();
    }

    public List<Link> findAllByChat(Chat chat) {
        return dsl.select().from(CHATS_TO_LINKS).join(LINKS).on(LINKS.ID.eq(CHATS_TO_LINKS.LINK_ID))
                .where(CHATS_TO_LINKS.CHAT_ID.eq((int) chat.getId())).fetch()
                .map(record -> recordMapper.map((LinksRecord) record));
    }

    public List<Link> findAll() {
        return dsl.select(LINKS).fetch()
                .map(record -> recordMapper.map((LinksRecord) record));
    }

    public List<Link> findOlderThan(int minutes) {
        return dsl.select().from(LINKS).where(currentTimestamp().minus(LINKS.LAST_UPDATE)
                        .greaterThan((Timestamp) val(Duration.ofMinutes(minutes).toMillis()))).fetch()
                .map(record -> recordMapper.map((LinksRecord) record));
    }

    public int addLinkToChat(Chat chat, Link link) {
        return dsl.insertInto(CHATS_TO_LINKS)
                .set(CHATS_TO_LINKS.CHAT_ID, (int) chat.getId())
                .set(CHATS_TO_LINKS.LINK_ID, (int) link.getId()).onConflictDoNothing()
                .execute();
    }

    public List<Long> getChatIds(Link link) {
        return dsl.select(CHATS_TO_LINKS)
                .where(CHATS_TO_LINKS.LINK_ID.eq((int) link.getId()))
                .fetch(CHATS_TO_LINKS.CHAT_ID, Long.class);
    }
}
