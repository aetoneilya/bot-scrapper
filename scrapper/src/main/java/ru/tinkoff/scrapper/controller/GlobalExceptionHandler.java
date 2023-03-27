package ru.tinkoff.scrapper.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.tinkoff.scrapper.dto.response.ApiErrorResponse;
import ru.tinkoff.scrapper.exception.ApiErrorException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> catchApiErrorException(ApiErrorException e) {
        log.error(e.getMessage(), e);

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();

        apiErrorResponse.setExceptionMessage(e.getMessage());
        List<String> stacktrace = new ArrayList<>();
        for (StackTraceElement stackTraceElement : e.getStackTrace())
            stacktrace.add(stackTraceElement.toString());
        apiErrorResponse.setStacktrace(stacktrace);
        apiErrorResponse.setExceptionName(e.getClass().getCanonicalName());
        apiErrorResponse.setCode(Integer.toString(e.getStatusCode()));
        apiErrorResponse.setDescription(e.getDescription());

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.valueOf(e.getStatusCode()));
    }
}
