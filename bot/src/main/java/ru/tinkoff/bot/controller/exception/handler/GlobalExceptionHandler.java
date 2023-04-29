package ru.tinkoff.bot.controller.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.tinkoff.bot.controller.dto.response.ApiErrorResponse;
import ru.tinkoff.bot.controller.exception.WrongParametersException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(WrongParametersException.class)
    public ResponseEntity<ApiErrorResponse> catchWrongParameters(WrongParametersException ex) {
        ApiErrorResponse response = createError(ex, BAD_REQUEST.getReasonPhrase(), String.valueOf(BAD_REQUEST));
        return ResponseEntity.status(BAD_REQUEST).body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> illegalArgument(IllegalArgumentException ex) {
        ApiErrorResponse response = createError(ex, BAD_REQUEST.getReasonPhrase(), String.valueOf(BAD_REQUEST));
        return ResponseEntity.status(BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> exception(Exception ex) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(createError(ex, INTERNAL_SERVER_ERROR.getReasonPhrase(), String.valueOf(INTERNAL_SERVER_ERROR)));
    }

    private ApiErrorResponse createError(Throwable exception, String description, String code) {

        List<String> stackTrace = new ArrayList<>();
        for (StackTraceElement element : exception.getStackTrace()) stackTrace.add(element.toString());

        return new ApiErrorResponse(description, code, exception.getClass().getCanonicalName(), exception.getMessage(), stackTrace);
    }
}



