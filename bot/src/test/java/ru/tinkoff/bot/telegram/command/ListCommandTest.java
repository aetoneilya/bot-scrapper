package ru.tinkoff.bot.telegram.command;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.tinkoff.bot.client.scrapper.ScrapperClient;
import ru.tinkoff.bot.client.scrapper.dto.response.LinkResponse;
import ru.tinkoff.bot.client.scrapper.dto.response.ListLinksResponse;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ListCommandTest {
    ScrapperClient scrapperClient = mock(ScrapperClient.class);
    private final ListCommand listCommand = new ListCommand(scrapperClient);

    @Test
    void listFourLinks() throws URISyntaxException {
        ListLinksResponse listLinksResponseUser0 = new ListLinksResponse(
                List.of(
                        new LinkResponse(1, new URI("https://google.com")),
                        new LinkResponse(2, new URI("https://bing.com")),
                        new LinkResponse(2, new URI("https://opera.com")),
                        new LinkResponse(3, new URI("https://yandex.com"))), 4);
        when(scrapperClient.getAllLinks(0)).thenReturn(listLinksResponseUser0);
        String expected = """
                Tracked links:
                https://google.com
                https://bing.com
                https://opera.com
                https://yandex.com""";
        Update update = BotUtils.parseUpdate("{\"message\"={\"text\"=\"/list\",\"chat\"={\"id\"= 0}}}");

        String answer = (String) listCommand.handle(update).getParameters().get("text");

        Assertions.assertEquals(expected, answer);
    }

    @Test
    void listTwoLinks() throws URISyntaxException {
        ListLinksResponse listLinksResponseUser1 = new ListLinksResponse(
                List.of(
                        new LinkResponse(1, new URI("https://google.com")),
                        new LinkResponse(3, new URI("https://yandex.com"))), 2);
        when(scrapperClient.getAllLinks(1)).thenReturn(listLinksResponseUser1);
        String expected = """
                Tracked links:
                https://google.com
                https://yandex.com""";
        Update update = BotUtils.parseUpdate("{\"message\"={\"text\"=\"/list\",\"chat\"={\"id\"= 1}}}");

        String answer = (String) listCommand.handle(update).getParameters().get("text");

        Assertions.assertEquals(expected, answer);
    }

    @Test
    void listEmptyResponse() {
        ListLinksResponse listLinksResponseUser2 = new ListLinksResponse(List.of(), 0);
        when(scrapperClient.getAllLinks(2)).thenReturn(listLinksResponseUser2);
        String expected = "No tracked links yet..\nFeel free to add some";
        Update update = BotUtils.parseUpdate("{\"message\"={\"text\"=\"/list\",\"chat\"={\"id\"= 2}}}");

        String answer = (String) listCommand.handle(update).getParameters().get("text");

        Assertions.assertEquals(expected, answer);
    }
}