package ru.tinkoff;

import java.net.URI;
import java.net.URISyntaxException;

public abstract class BaseUrlParser implements UrlParser {
    UrlParser next;

    @Override
    public void setNext(UrlParser parser) {
        next = parser;
    }

    @Override
    public UrlParserResponse parse(String url) {
        URI uri;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return parseUri(uri);
    }

    public UrlParserResponse parseUri(URI uri) {
        if (next != null) {
            return next.parseUri(uri);
        }
        return null;
    }
}
