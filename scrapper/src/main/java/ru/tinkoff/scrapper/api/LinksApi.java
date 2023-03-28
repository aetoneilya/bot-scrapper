package ru.tinkoff.scrapper.api;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.scrapper.dto.request.RemoveLinkRequest;
import ru.tinkoff.scrapper.dto.response.ApiErrorResponse;
import ru.tinkoff.scrapper.dto.response.LinkResponse;
import ru.tinkoff.scrapper.dto.response.ListLinksResponse;

@Validated
@Api(value = "links")
public interface LinksApi {
    @Operation(summary = "Получить все отслеживаемые ссылки", responses = {
            @ApiResponse(responseCode = "200", description = "Ссылки успешно получены", content = @Content(schema = @Schema(implementation = LinkResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))})
    @GetMapping(value = "/links", produces = {"application/json"})
    ListLinksResponse linksGet(@RequestHeader(value = "Tg-Chat-Id") Long tgChatId);

    @Operation(summary = "Добавить отслеживание ссылки", responses = {
            @ApiResponse(responseCode = "200", description = "Ссылка успешно добавлена", content = @Content(schema = @Schema(implementation = LinkResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))})
    @PostMapping(value = "/links", produces = {"application/json"}, consumes = {"application/json"})
    LinkResponse linksPost(@RequestHeader(value = "Tg-Chat-Id") Long tgChatId, @RequestBody AddLinkRequest body);

    @Operation(summary = "Убрать отслеживание ссылки", responses = {
            @ApiResponse(responseCode = "200", description = "Ссылка успешно убрана", content = @Content(schema = @Schema(implementation = LinkResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Ссылка не найдена", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))) })
    @DeleteMapping(value = "/links", produces = {"application/json"}, consumes = {"application/json"})
    LinkResponse linksDelete(@RequestHeader(value = "Tg-Chat-Id") Long tgChatId, @RequestBody RemoveLinkRequest body);

}
