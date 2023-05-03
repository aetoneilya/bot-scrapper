package ru.tinkoff.scrapper.domain.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import ru.tinkoff.scrapper.domain.dto.Chat;

import java.util.List;

import static ru.tinkoff.scrapper.domain.jooq.tables.Chats.CHATS;

@Repository
@RequiredArgsConstructor
public class JooqTgChatRepository {
    private final DSLContext dsl;

    public int add(Chat chat) {
        return dsl.insertInto(CHATS, CHATS.ID).values(chat.getId().intValue()).onConflictDoNothing().execute();
    }

    public int remove(Chat chat) {
        return dsl.deleteFrom(CHATS).where(CHATS.ID.equal(chat.getId().intValue())).execute();
    }

    public List<Chat> findAll() {
        return dsl.selectFrom(CHATS).fetchInto(Chat.class);
    }
}