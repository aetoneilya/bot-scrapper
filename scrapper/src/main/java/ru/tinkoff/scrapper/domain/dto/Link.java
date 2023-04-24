package ru.tinkoff.scrapper.domain.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "links")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "link_seq")
    @SequenceGenerator(name = "link_seq", sequenceName = "link_seq", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @Column(name = "link")
    private String link;

    @Column(name = "last_update")
    private Timestamp lastUpdate;

    @Column(name = "state")
    @JdbcTypeCode(SqlTypes.JSON)
    private String state;

    @ManyToMany(mappedBy = "links", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Chat> chats = new ArrayList<>();
}
