package ru.tinkoff.bot.telegram.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class TrackCommand  implements Command{
    @Override
    public String command() {
        return "/track";
    }

    @Override
    public String description() {
        return "Add link";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), "link added");
    }
}
