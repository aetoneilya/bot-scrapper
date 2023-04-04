package ru.tinkoff.scrapper.exception;

public class ResourceNotFoundException extends ApiErrorException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
