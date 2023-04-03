package ru.tinkoff.bot.telegram.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public class StartCommand  implements Command{
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
        return new SendMessage(update.message().chat().id(), "You are registered");
    }
}
