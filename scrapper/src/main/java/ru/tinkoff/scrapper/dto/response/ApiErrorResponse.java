package ru.tinkoff.scrapper.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

import java.util.List;

@Validated
@Data
@NoArgsConstructor
public class ApiErrorResponse {
    private String description = null;
    private String code = null;
    private String exceptionName = null;
    private String exceptionMessage = null;
    @Valid
    private List<String> stacktrace = null;
}