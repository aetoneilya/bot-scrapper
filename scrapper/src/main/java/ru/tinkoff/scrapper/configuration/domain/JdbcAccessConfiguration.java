package ru.tinkoff.scrapper.configuration.domain;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.scrapper.domain.jdbc.JdbcLinkRepository;
import ru.tinkoff.scrapper.domain.jdbc.JdbcTgChatRepository;
import ru.tinkoff.scrapper.service.LinkService;
import ru.tinkoff.scrapper.service.LinkUpdater;
import ru.tinkoff.scrapper.service.TgChatService;
import ru.tinkoff.scrapper.service.Utilities;
import ru.tinkoff.scrapper.service.jdbc.JdbcLinkService;
import ru.tinkoff.scrapper.service.jdbc.JdbcLinkUpdater;
import ru.tinkoff.scrapper.service.jdbc.JdbcTgChatService;

@Configuration
@ConditionalOnProperty(prefix = "scrapper", name = "access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {
    @Bean
    public LinkService linkService(JdbcLinkRepository jdbcLinkRepository, Utilities utilities) {
        return new JdbcLinkService(jdbcLinkRepository, utilities);
    }

    @Bean
    public TgChatService chatService(JdbcTgChatRepository jdbcChatRepository) {
        return new JdbcTgChatService(jdbcChatRepository);
    }

    @Bean
    public LinkUpdater linkUpdater(JdbcLinkRepository jdbcLinkRepository, Utilities utilities) {
        return new JdbcLinkUpdater(jdbcLinkRepository, utilities);
    }
}