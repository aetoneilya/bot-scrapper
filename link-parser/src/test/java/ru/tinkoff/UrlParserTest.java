package ru.tinkoff;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UrlParserTest {

    private static BaseUrlParser urlParser;

    @BeforeAll
    static void setUp() {
        urlParser = new GitHubUrlParser();
        urlParser.setNext(new StackOverflowUrlParser());
    }

    @Test
    void correctGithubLinkTest() {
        String link = "https://github.com/pengrad/java-telegram-bot-api";

        UrlParserResponse response = urlParser.parse(link);

        assertInstanceOf(GitHubResponse.class, response);
        GitHubResponse gitHubResponse = (GitHubResponse) response;
        assertEquals(gitHubResponse.repository(), "java-telegram-bot-api");
        assertEquals(gitHubResponse.user(), "pengrad");
    }
    @Test
    void wrongGithubLinkTest() {
        String link = "https://github.com/pengrad";

        UrlParserResponse response = urlParser.parse(link);

        assertNull(response);
    }
    @Test
    void correctOverflowLinkTest() {
        String link = "https://stackoverflow.com/questions/44593066/spring-webflux-webclient-get-body-on-error";

        UrlParserResponse response = urlParser.parse(link);

        assertInstanceOf(StackOverflowResponse.class, response);
        StackOverflowResponse gitHubResponse = (StackOverflowResponse) response;
        assertEquals(44593066L, gitHubResponse.id());
    }
    @Test
    void wrongOverflowLinkTest() {
        String link = "https://stackoverflow.com/questions/nonumber/spring-webflux-webclient-get-body-on-error";

        UrlParserResponse response = urlParser.parse(link);

        assertNull(response);
    }
    @Test
    void unsupportedLinkTest() {
        String link = "https://wowSuchAStrangeUnsupportedLink";

        UrlParserResponse response = urlParser.parse(link);

        assertNull(response);
    }
}