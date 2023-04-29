package ru.tinkoff.scrapper.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.tinkoff.scrapper.controller.dto.response.ApiErrorResponse;

@Validated
public interface TgChatApi {

    @Operation(summary = "Удалить чат", responses = {
            @ApiResponse(responseCode = "200", description = "Чат успешно удалён"),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Чат не существует", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))})
    @DeleteMapping(value = "/tg-chat/{id}",
            produces = {"application/json"})
    void tgChatIdDelete(@PathVariable("id") Long id);

    @Operation(summary = "Зарегистрировать чат", responses = {
            @ApiResponse(responseCode = "200", description = "Чат зарегистрирован"),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))})
    @PostMapping(value = "/tg-chat/{id}",
            produces = {"application/json"})
    void tgChatIdPost(@PathVariable("id") Long id);
}
