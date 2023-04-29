package ru.tinkoff.bot.controller.exception;

public class WrongParametersException extends ApiErrorException {
    public WrongParametersException(String message) {
        super(message);
    }
}
