package ru.tinkoff.scrapper.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class RemoveLinkRequest {
    @JsonProperty("link")
    private String link = null;
}

