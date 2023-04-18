package ru.tinkoff.scrapper.controller.exception;

public class ApiErrorException extends RuntimeException{
    public ApiErrorException(String message){
        super(message);
    }
}
