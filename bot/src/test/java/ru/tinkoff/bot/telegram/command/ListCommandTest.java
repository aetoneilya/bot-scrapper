package ru.tinkoff.bot.telegram.command;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Test;
import ru.tinkoff.bot.client.scrapper.ScrapperClient;
import ru.tinkoff.bot.client.scrapper.dto.response.LinkResponse;
import ru.tinkoff.bot.client.scrapper.dto.response.ListLinksResponse;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ListCommandTest {
    private final ListLinksResponse listLinksResponseUser0 = new ListLinksResponse(
            List.of(
                    new LinkResponse(1, new URI("https://google.com")),
                    new LinkResponse(2, new URI("https://bing.com")),
                    new LinkResponse(2, new URI("https://opera.com")),
                    new LinkResponse(3, new URI("https://yandex.com"))), 4);
    private final ListLinksResponse listLinksResponseUser1 = new ListLinksResponse(
            List.of(
                    new LinkResponse(1, new URI("https://google.com")),
                    new LinkResponse(3, new URI("https://yandex.com"))), 2);
    private final ListLinksResponse listLinksResponseUser2 = new ListLinksResponse(List.of(), 0);
    private final ListCommand listCommand;

    ListCommandTest() throws URISyntaxException {
        ScrapperClient scrapperClient = mock(ScrapperClient.class);
        when(scrapperClient.getAllLinks(0)).thenReturn(listLinksResponseUser0);
        when(scrapperClient.getAllLinks(1)).thenReturn(listLinksResponseUser1);
        when(scrapperClient.getAllLinks(2)).thenReturn(listLinksResponseUser2);
        listCommand = new ListCommand(scrapperClient);
    }

    @Test
    void testMessageText() {
        correctSendMessage(0L, listLinksResponseUser0);
        correctSendMessage(1L, listLinksResponseUser1);
        correctSendMessage(2L, listLinksResponseUser2);
    }

    void correctSendMessage(Long userId, ListLinksResponse response) {
        Update update = BotUtils.parseUpdate("{\"message\"={\"text\"=\"/list\",\"chat\"={\"id\"=" + userId + "}}}");

        String answer = (String) listCommand.handle(update).getParameters().get("text");


        if (response.size() > 0) {
            StringBuilder sendMessageText = new StringBuilder("Tracked links:");
            for (LinkResponse linkResponse : response.links()) {
                sendMessageText.append("\n").append(linkResponse.url().toString());
            }
            assertEquals(answer, sendMessageText.toString());
        } else {
            assertEquals(answer, "No tracked links yet..\nFeel free to add some");
        }
    }

}