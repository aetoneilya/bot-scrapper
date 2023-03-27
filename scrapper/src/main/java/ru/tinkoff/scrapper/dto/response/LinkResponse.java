package ru.tinkoff.scrapper.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class LinkResponse {
    @JsonProperty("id")
    private Long id = null;

    @JsonProperty("url")
    private String url = null;
}
