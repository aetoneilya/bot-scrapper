package ru.tinkoff;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseUrlParserTest {

    private static BaseUrlParser urlParser;

    @Test
    void unsupportedLinkTest() {
        String link = "https://wowSuchAStrangeUnsupportedLink";
        urlParser = new GitHubUrlParser();
        urlParser.setNext(new StackOverflowUrlParser());

        UrlParserResponse response = urlParser.parse(link);

        assertNull(response);
    }

    @Test
    void unsupportedLinkTestDuplicateParser() {
        String link = "https://wowSuchAStrangeUnsupportedLink";
        urlParser = new GitHubUrlParser();
        urlParser.setNext(new StackOverflowUrlParser());
        urlParser.setNext(new GitHubUrlParser());
        urlParser.setNext(new StackOverflowUrlParser());
        urlParser.setNext(new GitHubUrlParser());
        urlParser.setNext(new StackOverflowUrlParser());
        urlParser.setNext(new GitHubUrlParser());


        UrlParserResponse response = urlParser.parse(link);

        assertNull(response);
    }
}