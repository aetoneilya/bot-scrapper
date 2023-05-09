package ru.tinkoff;

import java.net.URI;

public final class GitHubUrlParser extends BaseUrlParser {
    private static final String HOST = "github.com";
    private static final int MAX_WORDS_IN_LINK = 3;

    @Override
    public UrlParserResponse parseUri(URI uri) {
        if (uri.getHost().equals(HOST)) {
            String[] parsedLink = uri.getPath().split("/");
            if (parsedLink.length < MAX_WORDS_IN_LINK) {
                return null;
            }
            String user = parsedLink[1];
            String repo = parsedLink[2];
            return new GitHubLink(user, repo);
        } else {
            return super.parseUri(uri);
        }
    }
}

