package ru.tinkoff.scrapper.scheduler;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;


@Component
@EnableScheduling
public class LinkUpdaterScheduler {
       private static final Logger log = Logger.getLogger(LinkUpdaterScheduler.class.getName());

    @Scheduled(fixedDelayString = "#{@delay}")
    public void update() {
        log.log(Level.INFO, "Купи слона");
    }
}
