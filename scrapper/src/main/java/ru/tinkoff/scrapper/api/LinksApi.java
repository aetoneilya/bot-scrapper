package ru.tinkoff.scrapper.api;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.scrapper.dto.request.AddLinkRequest;
import ru.tinkoff.scrapper.dto.request.RemoveLinkRequest;
import ru.tinkoff.scrapper.dto.response.LinkResponse;
import ru.tinkoff.scrapper.dto.response.ListLinksResponse;

@Validated
@Api(value = "links", description = "the links API")
public interface LinksApi {
    @Operation(summary = "Получить все отслеживаемые ссылки", tags = {})
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Ссылки успешно получены", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ListLinksResponse.class))),
//            @ApiResponse(code = 400, message = "Некорректные параметры запроса", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))})
    @GetMapping(value = "/links",
            produces = {"application/json"})
    ListLinksResponse linksGet(@RequestHeader(value = "Tg-Chat-Id") Long tgChatId);

    //    ListLinksResponse linksGet(@Parameter(in = ParameterIn.HEADER, description = "", required = true, schema = @Schema()) @RequestHeader(value = "Tg-Chat-Id", required = true) Long tgChatId);
    @Operation(summary = "Добавить отслеживание ссылки")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Ссылка успешно добавлена", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LinkResponse.class))),
//
//            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))})
    @PostMapping(value = "/links",
            produces = {"application/json"},
            consumes = {"application/json"})
    LinkResponse linksPost(@RequestHeader(value = "Tg-Chat-Id") Long tgChatId, @RequestBody AddLinkRequest body);
//    ResponseEntity<LinkResponse> linksPost(@Parameter(in = ParameterIn.HEADER, description = "", required = true, schema = @Schema()) @RequestHeader(value = "Tg-Chat-Id", required = true) Long tgChatId, @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody AddLinkRequest body);

    @Operation(summary = "Убрать отслеживание ссылки")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Ссылка успешно убрана", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LinkResponse.class))),
//            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
//            @ApiResponse(responseCode = "404", description = "Ссылка не найдена", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))) })
    @DeleteMapping(value = "/links",
            produces = {"application/json"},
            consumes = {"application/json"})
    LinkResponse linksDelete(@RequestHeader(value = "Tg-Chat-Id") Long tgChatId, @RequestBody RemoveLinkRequest body);
//    ResponseEntity<LinkResponse> linksDelete(@Parameter(in = ParameterIn.HEADER, description = "", required = true, schema = @Schema()) @RequestHeader(value = "Tg-Chat-Id", required = true) Long tgChatId, @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema()) @Valid @RequestBody RemoveLinkRequest body);

}
