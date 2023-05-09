package ru.tinkoff.scrapper.service.jooq;

import java.sql.Timestamp;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import ru.tinkoff.scrapper.client.tgbot.TelegramBotClient;
import ru.tinkoff.scrapper.domain.dto.Chat;
import ru.tinkoff.scrapper.domain.dto.Link;
import ru.tinkoff.scrapper.domain.jooq.JooqLinkRepository;
import ru.tinkoff.scrapper.service.LinkUpdater;
import ru.tinkoff.scrapper.service.Utilities;

@RequiredArgsConstructor
public class JooqLinkUpdater implements LinkUpdater {
    private final JooqLinkRepository linkRepository;
    private final Utilities utilities;
    private final TelegramBotClient telegramBotClient;

    @Value("${scrapper.update-frequency}")
    int updateFrequency;

    @Override
    public int update() {
        List<Link> oldLinks = linkRepository.findOlderThan(updateFrequency);

        for (Link link : oldLinks) {
            String newState = utilities.getNewState(link);
            if (!newState.equals(link.getState())) {
                sendUpdate(link, newState);
                saveNewState(link, newState);
            }
        }

        return 0;
    }

    private void sendUpdate(Link link, String newState) {
        linkRepository.getChatsIds(link).forEach((x) -> link.getChats().add(new Chat(x, null)));
        telegramBotClient.sendUpdateToBot(link, newState);
    }

    private void saveNewState(Link link, String newState) {
        link.setState(newState);
        link.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        linkRepository.update(link);
    }
}
