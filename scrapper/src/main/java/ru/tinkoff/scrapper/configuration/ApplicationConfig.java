package ru.tinkoff.scrapper.configuration;

import com.google.gson.Gson;
import jakarta.validation.constraints.NotNull;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.GitHubUrlParser;
import ru.tinkoff.StackOverflowUrlParser;
import ru.tinkoff.UrlParser;
import ru.tinkoff.scrapper.client.tgbot.TelegramBotClient;
import ru.tinkoff.scrapper.scheduler.Scheduler;
import ru.tinkoff.scrapper.service.updatesender.HttpUpdateSender;
import ru.tinkoff.scrapper.service.updatesender.RabbitQueueProducer;
import ru.tinkoff.scrapper.service.updatesender.UpdateSender;

@Validated
@ConfigurationProperties(prefix = "scrapper", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotNull String test,
    @NotNull Scheduler scheduler,
    @NotNull int updateFrequency,
    @NotNull AccessType accessType,
    @NotNull boolean useQueue) {
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
    public UpdateSender getUpdateSender(RabbitTemplate rabbitTemplate, Binding binding, TelegramBotClient client) {
        if (useQueue) {
            return new RabbitQueueProducer(rabbitTemplate, binding);
        } else {
            return new HttpUpdateSender(client);
        }
    }

    @Bean
    public Gson getGson() {
        return new Gson();
    }
}
