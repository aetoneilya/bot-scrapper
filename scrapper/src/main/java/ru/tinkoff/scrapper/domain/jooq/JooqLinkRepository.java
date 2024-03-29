package ru.tinkoff.scrapper.domain.jooq;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.JSON;
import org.jooq.RecordMapper;
import org.jooq.types.DayToSecond;
import org.springframework.stereotype.Repository;
import ru.tinkoff.scrapper.domain.dto.Chat;
import ru.tinkoff.scrapper.domain.dto.Link;
import ru.tinkoff.scrapper.domain.jooq.generated.tables.records.LinksRecord;
import static org.jooq.impl.DSL.currentLocalDateTime;
import static org.jooq.impl.DSL.localDateTimeAdd;
import static ru.tinkoff.scrapper.domain.jooq.generated.Tables.CHATS_TO_LINKS;
import static ru.tinkoff.scrapper.domain.jooq.generated.tables.Links.LINKS;

@Repository
@RequiredArgsConstructor
public class JooqLinkRepository {
    private static final RecordMapper<LinksRecord, Link> RECORD_MAPPER = r -> new Link(
        Long.valueOf(r.getId()),
        r.getLink(),
        Timestamp.valueOf(r.getLastUpdate()),
        r.getState().toString(),
        new ArrayList<>()
    );

    private final DSLContext dsl;

    public Long add(Link link) {
        dsl.insertInto(LINKS)
            .set(LINKS.LINK, link.getLink())
            .set(LINKS.LAST_UPDATE, link.getLastUpdate().toLocalDateTime())
            .set(LINKS.STATE, JSON.json(link.getState()))
            .onConflictDoNothing().execute();
        return dsl.lastID().longValue();
    }

    public Link getByUrl(String url) {
        return dsl.selectFrom(LINKS).where(LINKS.LINK.eq(url)).fetchOne(RECORD_MAPPER);
    }

    public int update(Link link) {
        return dsl.update(LINKS)
            .set(LINKS.LINK, link.getLink())
            .set(LINKS.LAST_UPDATE, link.getLastUpdate().toLocalDateTime())
            .set(LINKS.STATE, JSON.json(link.getState()))
            .where(LINKS.ID.eq(link.getId().intValue())).execute();
    }

    public int remove(Chat chat, Link link) {
        return dsl.delete(CHATS_TO_LINKS)
            .where(CHATS_TO_LINKS.LINK_ID.eq(link.getId().intValue()))
            .and(CHATS_TO_LINKS.CHAT_ID.eq(chat.getId().intValue())).execute();
    }

    public List<Link> findAllByChat(Chat chat) {
        return dsl.select(LINKS.ID, LINKS.LINK, LINKS.LAST_UPDATE, LINKS.STATE).from(CHATS_TO_LINKS).join(LINKS)
            .on(LINKS.ID.eq(CHATS_TO_LINKS.LINK_ID))
            .where(CHATS_TO_LINKS.CHAT_ID.eq(chat.getId().intValue())).fetch()
            .map(r -> new Link(
                r.get(LINKS.ID, Long.class),
                r.get(LINKS.LINK, String.class),
                Timestamp.valueOf(r.get(LINKS.LAST_UPDATE, LocalDateTime.class)),
                r.get(LINKS.STATE, JSON.class).toString(),
                new ArrayList<>()
            ));
    }

    public List<Link> findAll() {
        return dsl.selectFrom(LINKS).fetch(RECORD_MAPPER);
    }

    public List<Link> findOlderThan(int minutes) {
        return dsl.selectFrom(LINKS)
            .where(LINKS.LAST_UPDATE.lessThan(localDateTimeAdd(
                currentLocalDateTime(),
                new DayToSecond(0, 0, minutes).neg()
            )))
            .fetch().map(RECORD_MAPPER);
    }

    public int addLinkToChat(Chat chat, Link link) {
        return dsl.insertInto(CHATS_TO_LINKS)
            .set(CHATS_TO_LINKS.CHAT_ID, chat.getId().intValue())
            .set(CHATS_TO_LINKS.LINK_ID, link.getId().intValue()).onConflictDoNothing()
            .execute();
    }

    public List<Long> getChatsIds(Link link) {
        return dsl.selectFrom(CHATS_TO_LINKS)
            .where(CHATS_TO_LINKS.LINK_ID.eq(link.getId().intValue()))
            .fetch(CHATS_TO_LINKS.CHAT_ID, Long.class);
    }
}
