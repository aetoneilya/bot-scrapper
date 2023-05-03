package ru.tinkoff.scrapper.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    Long id;
    List<Link> links = new ArrayList<>();
}
