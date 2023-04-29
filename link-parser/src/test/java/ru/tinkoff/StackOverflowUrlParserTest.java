package ru.tinkoff;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StackOverflowUrlParserTest {
    private final BaseUrlParser urlParser = new StackOverflowUrlParser();

    @Test
    void correctOverflowLinkTest() {
        String link = "https://stackoverflow.com/questions/44593066/spring-webflux-webclient-get-body-on-error";

        UrlParserResponse response = urlParser.parse(link);

        assertInstanceOf(StackOverflowLink.class, response);
        StackOverflowLink gitHubResponse = (StackOverflowLink) response;
        assertEquals(44593066L, gitHubResponse.id());
    }
    @Test
    void wrongOverflowLinkTest() {
        String link = "https://stackoverflow.com/questions/nonumber/spring-webflux-webclient-get-body-on-error";

        UrlParserResponse response = urlParser.parse(link);

        assertNull(response);
    }
    @Test
    void correctOverflowLinkTestInPair() {
        urlParser.setNext(new GitHubUrlParser());
        String link = "https://stackoverflow.com/questions/44593066/spring-webflux-webclient-get-body-on-error";

        UrlParserResponse response = urlParser.parse(link);

        assertInstanceOf(StackOverflowLink.class, response);
        StackOverflowLink gitHubResponse = (StackOverflowLink) response;
        assertEquals(44593066L, gitHubResponse.id());
    }
    @Test
    void wrongOverflowLinkTestInPair() {
        urlParser.setNext(new GitHubUrlParser());
        String link = "https://stackoverflow.com/questions/nonumber/spring-webflux-webclient-get-body-on-error";

        UrlParserResponse response = urlParser.parse(link);

        assertNull(response);
    }

    @Test
    void unsupportedLinkTest() {
        String link = "https://github.com/pengrad";

        UrlParserResponse response = urlParser.parse(link);

        assertNull(response);
    }
}
