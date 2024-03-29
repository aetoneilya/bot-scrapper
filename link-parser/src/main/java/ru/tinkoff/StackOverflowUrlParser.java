package ru.tinkoff;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StackOverflowUrlParser extends BaseUrlParser {
    private static final String HOST = "stackoverflow.com";
    private static final Pattern REGEX = Pattern.compile("^/questions/(\\d+)/.*$");

    @Override
    public UrlParserResponse parseUri(URI uri) {
        if (uri.getHost().equals(HOST)) {
            Matcher matcher = REGEX.matcher(uri.getPath());
            return matcher.matches() ? new StackOverflowLink(Long.parseLong(matcher.group(1))) : null;
        } else {
            return  super.parseUri(uri);
        }
    }
}

