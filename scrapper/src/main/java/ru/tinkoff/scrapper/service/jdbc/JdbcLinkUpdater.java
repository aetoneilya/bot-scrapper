package ru.tinkoff.scrapper.service.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import ru.tinkoff.scrapper.domain.dto.Link;
import ru.tinkoff.scrapper.domain.jdbc.JdbcLinkRepository;
import ru.tinkoff.scrapper.service.LinkUpdater;
import ru.tinkoff.scrapper.service.Utilities;

import java.sql.Timestamp;
import java.util.List;

@RequiredArgsConstructor
public class JdbcLinkUpdater implements LinkUpdater {

    private final JdbcLinkRepository linkRepository;
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
        link.setChats(linkRepository.getChats(link));
        utilities.sendUpdateToBot(link, newState);
    }

    private void saveNewState(Link link, String newState) {
        link.setState(newState);
        link.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        linkRepository.update(link);
    }

}
