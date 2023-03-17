package ru.tinkoff;

import java.net.URI;

public final class GitHubUrlParser extends BaseUrlParser {
    private static final String HOST = "github.com";

    @Override
    public UrlParserResponse parseUri(URI uri) {
        if (uri.getHost().equals(HOST)) {
            String[] parsedLink = uri.getPath().split("/");
            String user = parsedLink[1];
            String repo = parsedLink[2];
            return new GitHubResponse(user, repo);
        } else {
            return super.parseUri(uri);
        }
    }
}

