package ru.tinkoff.scrapper.exception;

public class ResourceNotFoundException extends ApiErrorException {
    public ResourceNotFoundException(String message) {
        super(404, "Ресурс не найден", message);
    }

    public ResourceNotFoundException(String message, String description) {
        super(404, description, message);
    }
}
