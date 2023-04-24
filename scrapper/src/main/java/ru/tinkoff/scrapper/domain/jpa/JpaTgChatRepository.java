package ru.tinkoff.scrapper.domain.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tinkoff.scrapper.domain.dto.Chat;

public interface JpaTgChatRepository extends JpaRepository<Chat, Long> {
}
