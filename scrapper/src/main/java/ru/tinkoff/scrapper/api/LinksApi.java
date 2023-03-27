package ru.tinkoff.scrapper.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.tinkoff.scrapper.dto.response.ListLinksResponse;

import java.util.Optional;

@Validated
@Api(value = "links", description = "the links API")
public interface LinksApi {
    @Operation(summary = "Получить все отслеживаемые ссылки", description = "", tags = {})
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Ссылки успешно получены", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ListLinksResponse.class))),
//            @ApiResponse(code = 400, message = "Некорректные параметры запроса", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))})
    @RequestMapping(value = "/links",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ListLinksResponse linksGet(@Parameter(in = ParameterIn.HEADER, description = "", required = true, schema = @Schema()) @RequestHeader(value = "Tg-Chat-Id", required = true) Long tgChatId);

}
