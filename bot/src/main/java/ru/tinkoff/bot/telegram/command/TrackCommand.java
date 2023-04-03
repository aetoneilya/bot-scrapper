package ru.tinkoff.bot.telegram.command;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import ru.tinkoff.bot.client.scrapper.ScrapperClient;
import ru.tinkoff.bot.client.scrapper.exception.ScrapperClientException;

@RequiredArgsConstructor
public class TrackCommand implements Command {
    private static final String REPLY_TO_TEXT = "Send link as reply to this message to add it to tracked links";

    private final ScrapperClient scrapperClient;

    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "Add link";
    }

    @Override
    public boolean supports(Update update) {
        return update.message().text().trim().equals(command()) || isReply(update);
    }

    private boolean isReply(Update update) {
        Message reply = update.message().replyToMessage();
        return reply != null && reply.text().equals(REPLY_TO_TEXT);
    }

    @Override
    public SendMessage handle(Update update) {
        if (isReply(update))
            return handeReply(update);
        return new SendMessage(update.message().chat().id(), REPLY_TO_TEXT).replyMarkup(new ForceReply());
    }

    private SendMessage handeReply(Update update) {
        Long chatId = update.message().chat().id();
        String url = update.message().text().trim();
        String replyMessage = "Link added to track list";

        try {
            scrapperClient.addLink(chatId, url);
        } catch (ScrapperClientException ex) {
            replyMessage = ex.getMessage();
        } catch (RuntimeException ex) {
            replyMessage = "Something went wrong";
        }

        return new SendMessage(chatId, replyMessage);
    }
}
