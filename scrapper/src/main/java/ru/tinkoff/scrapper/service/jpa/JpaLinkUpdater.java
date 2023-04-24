package ru.tinkoff.scrapper.service.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import ru.tinkoff.scrapper.domain.dto.Link;
import ru.tinkoff.scrapper.domain.jpa.JpaLinkRepository;
import ru.tinkoff.scrapper.service.LinkUpdater;
import ru.tinkoff.scrapper.service.Utilities;

import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
public class JpaLinkUpdater implements LinkUpdater {
    private final JpaLinkRepository linkRepository;
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
//        List<Long> chats = linkRepository.getChatIds(link);
//        utilities.sendUpdateToBot(chats, link, newState);
    }

    private void saveNewState(Link link, String newState) {
        link.setState(newState);
        link.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        linkRepository.save(link);
    }

}
