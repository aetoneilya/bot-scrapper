package ru.tinkoff.scrapper.exception;

public class WrongParametersException extends ApiErrorException{
    public WrongParametersException(String message) {
        super(400, "Некорректные параметры запроса", message);
    }
}
