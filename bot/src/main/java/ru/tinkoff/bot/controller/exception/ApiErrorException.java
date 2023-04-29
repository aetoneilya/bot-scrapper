package ru.tinkoff.bot.controller.exception;

public class ApiErrorException extends RuntimeException{
    public ApiErrorException(String message){
        super(message);
    }
}
