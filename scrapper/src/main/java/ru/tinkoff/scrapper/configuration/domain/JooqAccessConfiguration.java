package ru.tinkoff.scrapper.configuration.domain;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.scrapper.domain.jooq.JooqLinkRepository;
import ru.tinkoff.scrapper.domain.jooq.JooqTgChatRepository;
import ru.tinkoff.scrapper.service.LinkService;
import ru.tinkoff.scrapper.service.LinkUpdater;
import ru.tinkoff.scrapper.service.TgChatService;
import ru.tinkoff.scrapper.service.Utilities;
import ru.tinkoff.scrapper.service.jooq.JooqLinkService;
import ru.tinkoff.scrapper.service.jooq.JooqLinkUpdater;
import ru.tinkoff.scrapper.service.jooq.JooqTgChatService;

@Configuration
@ConditionalOnProperty(prefix = "scrapper", name = "access-type", havingValue = "jooq")
public class JooqAccessConfiguration {
    @Bean
    public LinkService linkService(JooqLinkRepository linkRepository, Utilities utilities) {
        return new JooqLinkService(linkRepository, utilities);
    }

    @Bean
    public TgChatService chatService(JooqTgChatRepository chatRepository) {
        return new JooqTgChatService(chatRepository);
    }

    @Bean
    public LinkUpdater linkUpdater(JooqLinkRepository linkRepository, Utilities utilities) {
        return new JooqLinkUpdater(linkRepository, utilities);
    }
}