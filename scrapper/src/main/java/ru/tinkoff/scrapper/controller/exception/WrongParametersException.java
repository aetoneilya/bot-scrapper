package ru.tinkoff.scrapper.controller.exception;

public class WrongParametersException extends ApiErrorException{
    public WrongParametersException(String message) {
        super(message);
    }
}
