package ru.tinkoff.scrapper.controller.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.tinkoff.scrapper.controller.dto.response.ApiErrorResponse;
import ru.tinkoff.scrapper.controller.exception.WrongParametersException;
import ru.tinkoff.scrapper.controller.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> catchResourceNotFound(ResourceNotFoundException ex) {
        ApiErrorResponse response = createError(ex, NOT_FOUND.getReasonPhrase(), String.valueOf(NOT_FOUND));
        return ResponseEntity.status(NOT_FOUND).body(response);
    }

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
        ApiErrorResponse response = createError(ex, INTERNAL_SERVER_ERROR.getReasonPhrase(), String.valueOf(INTERNAL_SERVER_ERROR));
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(response);
    }

    private ApiErrorResponse createError(Throwable exception, String description, String code) {

        List<String> stackTrace = new ArrayList<>();
        for (StackTraceElement element : exception.getStackTrace()) stackTrace.add(element.toString());

        return new ApiErrorResponse(description, code, exception.getClass().getCanonicalName(), exception.getMessage(), stackTrace);
    }
}



