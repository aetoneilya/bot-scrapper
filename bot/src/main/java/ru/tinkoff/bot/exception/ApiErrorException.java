package ru.tinkoff.bot.exception;

public class ApiErrorException extends RuntimeException{
    public ApiErrorException(String message){
        super(message);
    }
}
