package ru.tinkoff.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.tinkoff.bot.configuration.ApplicationConfig;
import ru.tinkoff.bot.telegram.Bot;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
public class BotApplication {
    public static void main(String[] args) {
        var ctx = SpringApplication.run(BotApplication.class, args);
        ApplicationConfig config = ctx.getBean(ApplicationConfig.class);
        System.out.println(config);

        Bot bot = new Bot("6150282267:AAF106wPxccAev5fxpFA3bfVyRQr8xTvXuA");
        bot.start();
    }
}