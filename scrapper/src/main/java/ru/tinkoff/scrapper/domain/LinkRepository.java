package ru.tinkoff.scrapper.domain;

import ru.tinkoff.scrapper.domain.dto.Chat;
import ru.tinkoff.scrapper.domain.dto.Link;

import java.util.List;

public interface LinkRepository {
    Link add(Link link);
    int update(Link link);
    Link remove(Link link);
    List<Link> findAll(Chat chat);
    List<Link> findOlderThan(int minutes);
}
