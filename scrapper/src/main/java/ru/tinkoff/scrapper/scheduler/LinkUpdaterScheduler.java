package ru.tinkoff.scrapper.scheduler;

import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.scrapper.service.LinkUpdater;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class LinkUpdaterScheduler {
    private static final Logger LOG = Logger.getLogger(LinkUpdaterScheduler.class.getName());
    private final LinkUpdater linkUpdater;

    @Scheduled(fixedDelayString = "#{@delay}")
    public void update() {
        LOG.log(Level.INFO, "Updating old links");
        linkUpdater.update();
    }
}
