package ru.tinkoff.scrapper.domain.jpa;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tinkoff.scrapper.domain.dto.Link;

public interface JpaLinkRepository extends JpaRepository<Link, Long> {
    Optional<Link> findByLink(String link);

    @Query("select l from Link l where (CURRENT_TIMESTAMP - l.lastUpdate) > (:interval) ")
    List<Link> findOlderThan(@Param("interval") Duration minutes);
}
