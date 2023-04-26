package ru.tinkoff.scrapper.service.jooq;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tinkoff.scrapper.domain.dto.Chat;
import ru.tinkoff.scrapper.domain.dto.Link;
import ru.tinkoff.scrapper.domain.jooq.JooqLinkRepository;
import ru.tinkoff.scrapper.service.LinkUpdater;
import ru.tinkoff.scrapper.service.Utilities;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JooqLinkUpdater implements LinkUpdater {
    private final JooqLinkRepository linkRepository;
    private final Utilities utilities;

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
        utilities.sendUpdateToBot(link, newState);
    }

    private void saveNewState(Link link, String newState) {
        link.setState(newState);
        link.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        linkRepository.update(link);
    }
}
