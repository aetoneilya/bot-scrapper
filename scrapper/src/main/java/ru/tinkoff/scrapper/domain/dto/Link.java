package ru.tinkoff.scrapper.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Link {
    Long id;
    String link;
    Timestamp lastUpdate;
    String state;
    List<Chat> chats = new ArrayList<>();
}
