package ru.tinkoff.scrapper.exception;

import lombok.Getter;

public class ApiErrorException extends RuntimeException{
    @Getter
    private final int statusCode;
    @Getter
    private String description = "";
    public ApiErrorException(int statusCode, String message){
        super(message);
        this.statusCode = statusCode;
    }
    public ApiErrorException(int statusCode, String description, String message){
        super(message);
        this.statusCode = statusCode;
        this.description = description;
    }
}
