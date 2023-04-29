package ru.tinkoff.bot.api;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.tinkoff.bot.controller.dto.request.UpdatesRequest;
import ru.tinkoff.bot.controller.dto.response.ApiErrorResponse;

@Validated
@Api(value = "Bot API")
public interface BotApi {
    @Operation(summary = "Отправить обновление", responses = {
            @ApiResponse(responseCode = "200", description = "Обновление обработано"),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))})
    @PostMapping(value = "/updates", consumes = {"application/json"})
    void updatesPost(@RequestBody UpdatesRequest request);
}
