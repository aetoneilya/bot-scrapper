package ru.tinkoff.scrapper.service.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.scrapper.domain.dto.Chat;
import ru.tinkoff.scrapper.domain.dto.Link;
import ru.tinkoff.scrapper.domain.jpa.JpaLinkRepository;
import ru.tinkoff.scrapper.domain.jpa.JpaTgChatRepository;
import ru.tinkoff.scrapper.service.LinkService;
import ru.tinkoff.scrapper.service.Utilities;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
public class JpaLinkService implements LinkService {

    private final JpaTgChatRepository tgChatRepository;
    private final JpaLinkRepository linkRepository;
    private final Utilities utilities;

    @Override
    @Transactional
    public Link add(long tgChatId, URI url) {
        Link link = linkRepository.findByLink(url.toString()).orElse(utilities.createLink(url.toString()));

        Chat chat = tgChatRepository.findById(tgChatId)
                .orElseThrow(() -> new RuntimeException("Chat with id " + tgChatId + "not found"));
        link.getChats().add(chat);
        linkRepository.save(link);
        chat.getLinks().add(link);
        tgChatRepository.save(chat);
        return link;
    }

    @Override
    public Link remove(long tgChatId, URI url) {
        Link link = linkRepository.findByLink(url.toString()).orElseThrow(() -> new RuntimeException("Link " + url + " not found"));
        Chat chat = tgChatRepository.findById(tgChatId)
                .orElseThrow(() -> new RuntimeException("Chat with id " + tgChatId + "not found"));
        chat.getLinks().remove(link);
        return linkRepository.save(link);
    }

    @Override
    public List<Link> listAll(long tgChatId) {
        Chat chats = tgChatRepository.findById(tgChatId)
                .orElseThrow(() -> new RuntimeException("Chat with id " + tgChatId + "not found"));

        return chats.getLinks();
    }
}
