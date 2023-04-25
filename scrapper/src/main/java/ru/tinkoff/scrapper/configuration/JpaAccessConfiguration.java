package ru.tinkoff.scrapper.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.scrapper.domain.jpa.JpaLinkRepository;
import ru.tinkoff.scrapper.domain.jpa.JpaTgChatRepository;
import ru.tinkoff.scrapper.service.LinkService;
import ru.tinkoff.scrapper.service.LinkUpdater;
import ru.tinkoff.scrapper.service.TgChatService;
import ru.tinkoff.scrapper.service.Utilities;
import ru.tinkoff.scrapper.service.jpa.JpaLinkService;
import ru.tinkoff.scrapper.service.jpa.JpaLinkUpdater;
import ru.tinkoff.scrapper.service.jpa.JpaTgChatService;

@Configuration
@ConditionalOnProperty(prefix = "scrapper", name = "access-type", havingValue = "jpa")
public class JpaAccessConfiguration {
    @Bean
    public LinkService linkService(JpaLinkRepository linkRepository, JpaTgChatRepository chatRepository, Utilities utilities) {
        return new JpaLinkService(chatRepository, linkRepository, utilities);
    }

    @Bean
    public TgChatService chatService(JpaTgChatRepository chatRepository) {
        return new JpaTgChatService(chatRepository);
    }

    @Bean
    public LinkUpdater linkUpdater(JpaLinkRepository linkRepository, Utilities utilities) {
        return new JpaLinkUpdater(linkRepository, utilities);
    }
}