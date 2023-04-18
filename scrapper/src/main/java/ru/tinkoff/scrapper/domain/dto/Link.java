package ru.tinkoff.scrapper.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Link {
    long id;
    String link;
    Chat chat;
    Timestamp lastUpdate;
    String jsonState;
}
