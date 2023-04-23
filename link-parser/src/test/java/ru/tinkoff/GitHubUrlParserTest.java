package ru.tinkoff;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GitHubUrlParserTest {
    private final BaseUrlParser urlParser = new GitHubUrlParser();

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
    void correctGithubLinkTestInPair() {
        urlParser.setNext(new StackOverflowUrlParser());
        String link = "https://github.com/pengrad/java-telegram-bot-api";

        UrlParserResponse response = urlParser.parse(link);

        assertInstanceOf(GitHubResponse.class, response);
        GitHubResponse gitHubResponse = (GitHubResponse) response;
        assertEquals(gitHubResponse.repository(), "java-telegram-bot-api");
        assertEquals(gitHubResponse.user(), "pengrad");
    }
    @Test
    void wrongGithubLinkTestInPair() {
        urlParser.setNext(new StackOverflowUrlParser());
        String link = "https://github.com/pengrad";

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
