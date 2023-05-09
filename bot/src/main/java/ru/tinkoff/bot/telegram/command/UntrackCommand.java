package ru.tinkoff.bot.telegram.command;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.bot.client.scrapper.ScrapperClient;
import ru.tinkoff.bot.client.scrapper.exception.ScrapperClientException;

@Component
@RequiredArgsConstructor
public class UntrackCommand implements Command {
    private static final String REPLY_TO_TEXT = "Send link as reply to this message to remove it from tracked links";
    private final ScrapperClient scrapperClient;

    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "Untrack link";
    }

    private boolean isReply(Update update) {
        Message reply = update.message().replyToMessage();
        return reply != null && reply.text().equals(REPLY_TO_TEXT);
    }

    @Override
    public SendMessage handle(Update update) {
        if (isReply(update)) {
            return handeReply(update);
        }
        return new SendMessage(update.message().chat().id(), REPLY_TO_TEXT).replyMarkup(new ForceReply());
    }

    @Override
    public boolean supports(Update update) {
        return update.message().text().trim().equals(command()) || isReply(update);
    }

    private SendMessage handeReply(Update update) {
        Long chatId = update.message().chat().id();
        String url = update.message().text().trim();
        String replyMessage = "Link removed from track list";

        try {
            scrapperClient.removeLink(chatId, url);
        } catch (ScrapperClientException ex) {
            replyMessage = ex.getMessage();
        } catch (RuntimeException ex) {
            replyMessage = "Something went wrong";
        }

        return new SendMessage(chatId, replyMessage);
    }
}
