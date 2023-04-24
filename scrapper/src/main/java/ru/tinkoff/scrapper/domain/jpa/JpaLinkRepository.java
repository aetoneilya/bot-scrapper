package ru.tinkoff.scrapper.domain.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tinkoff.scrapper.domain.dto.Link;

import java.util.List;
import java.util.Optional;

public interface JpaLinkRepository extends JpaRepository<Link, Long> {
    Optional<Link> findByLink(String link);

    @Query(value = "select *, now() - last_update from links where (now() - last_update) > (:interval * interval '1 minute')", nativeQuery = true)
    List<Link> findOlderThan(@Param("interval") int minutes);
}
