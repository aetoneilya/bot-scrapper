package ru.tinkoff.bot.telegram.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class HelpCommand implements Command {

    @Setter
    @Getter
    private String helpMessage;

    @Autowired
    public HelpCommand(List<Command> commands) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Command command : commands)
            stringBuilder.append(command.command()).append(" ").append(command.description()).append("\n");
        stringBuilder.append(command()).append(" ").append(description()).append("\n");
        helpMessage = stringBuilder.toString();
    }

    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String description() {
        return "Available commands";
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), helpMessage);
    }
}
