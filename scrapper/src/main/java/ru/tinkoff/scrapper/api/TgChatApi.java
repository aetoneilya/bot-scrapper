package ru.tinkoff.scrapper.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
public interface TgChatApi {

    @Operation(summary = "Удалить чат", description = "", tags={  })
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Чат успешно удалён"),
//            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
//            @ApiResponse(responseCode = "404", description = "Чат не существует", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))) })
    @DeleteMapping(value = "/tg-chat/{id}",
            produces = { "application/json" })
    void tgChatIdDelete(@PathVariable("id") Long id);
//    void tgChatIdDelete(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("id") Long id);


    @Operation(summary = "Зарегистрировать чат", description = "", tags={  })
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Чат зарегистрирован"),
//            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))) })
    @PostMapping(value = "/tg-chat/{id}",
            produces = { "application/json" })
    void tgChatIdPost(@PathVariable("id") Long id);
//    void tgChatIdPost(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("id") Long id);

}
