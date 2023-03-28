package ru.tinkoff.bot.exception;

public class WrongParametersException extends ApiErrorException {
    public WrongParametersException(String message) {
        super(message);
    }
}
