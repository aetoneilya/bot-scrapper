package ru.tinkoff;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StackOverflowUrlParser extends BaseUrlParser {
    static final String host = "stackoverflow.com";
    static final Pattern regex = Pattern.compile("^/questions/(\\d+)/.*$");

    @Override
    public UrlParserResponse parseUri(URI uri) {
        if (uri.getHost().equals(host)) {
            Matcher matcher = regex.matcher(uri.getPath());
//            String res = matcher.matches() ? matcher.group(1) : "None";
            return matcher.matches() ? new StackOverflowResponse(Integer.parseInt(matcher.group(1))) : null;
        } else {
            return  super.parseUri(uri);
        }
    }
}

record StackOverflowResponse(int id) implements UrlParserResponse {
}
