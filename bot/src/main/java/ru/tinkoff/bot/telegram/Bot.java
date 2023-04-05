package ru.tinkoff.bot.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.bot.telegram.command.Command;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Bot {
    private final TelegramBot bot;
    private final List<Command> commands;

    public void start() {
        BotCommand[] menuCommand = new BotCommand[commands.size()];
        for (int i = 0; i < commands.size(); i++) menuCommand[i] = commands.get(i).toApiCommand();

        bot.execute(new SetMyCommands(menuCommand));
        bot.setUpdatesListener(updates -> {
            for (Update update : updates)
                bot.execute(processUpdate(update));

            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }

    public SendMessage processUpdate(Update update) {
        for (Command command : commands)
            if (command.supports(update)) return command.handle(update);

        return new SendMessage(update.message().chat().id(), "Unsupported command");
    }
}
