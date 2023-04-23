package ru.tinkoff.bot.telegram;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import ru.tinkoff.bot.telegram.command.StartCommand;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BotTest {
    @Test
    void unknownCommand() {
        Bot bot = new Bot(null, List.of(new StartCommand(null)));
        Update update = BotUtils.parseUpdate("{\"message\"={\"text\"=\"/unknownCommand\",\"chat\"={\"id\"=0}}}");

        SendMessage msg = bot.processUpdate(update);

        String text = (String) msg.getParameters().get("text");
        assertEquals("Unsupported command", text);
    }
}