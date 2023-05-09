package ru.tinkoff.bot.telegram.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.bot.client.scrapper.ScrapperClient;
import ru.tinkoff.bot.client.scrapper.exception.ScrapperClientException;

@Component
@RequiredArgsConstructor
public class StartCommand implements Command {
    private final ScrapperClient scrapperClient;

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "register";
    }

    @Override
    public SendMessage handle(Update update) {
        Long chatId = update.message().chat().id();

        String replyMessage = "You are registered! Add some github or stackoverflow links";

        try {
            scrapperClient.addChat(chatId);
        } catch (ScrapperClientException ex) {
            replyMessage = ex.getMessage();
        } catch (RuntimeException ex) {
            replyMessage = "@#!@$ I'm broken!?> This is don't supposed to happen";
        }

        return new SendMessage(chatId, replyMessage);
    }
}
