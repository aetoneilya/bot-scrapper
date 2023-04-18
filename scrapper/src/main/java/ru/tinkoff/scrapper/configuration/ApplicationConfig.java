package ru.tinkoff.scrapper.configuration;

import com.google.gson.Gson;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.GitHubUrlParser;
import ru.tinkoff.StackOverflowUrlParser;
import ru.tinkoff.UrlParser;
import ru.tinkoff.scrapper.scheduler.Scheduler;

@Validated
@ConfigurationProperties(prefix = "scrapper", ignoreUnknownFields = false)
public record ApplicationConfig(@NotNull String test, @NotNull Scheduler scheduler, @NotNull int updateFrequency) {
    @Bean("delay")
    public long getDelay() {
        return scheduler.interval().toMillis();
    }

    @Bean
    public UrlParser getLinkParser() {
        UrlParser parser = new GitHubUrlParser();
        parser.setNext(new StackOverflowUrlParser());
        return parser;
    }

    @Bean
    public Gson getGson() {
        return new Gson();
    }
}