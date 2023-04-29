package ru.tinkoff.scrapper.controller.exception;

public class ResourceNotFoundException extends ApiErrorException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
