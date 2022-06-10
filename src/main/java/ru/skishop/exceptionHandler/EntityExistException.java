package ru.skishop.exceptionHandler;

import org.springframework.http.HttpStatus;

public class EntityExistException extends ApiException {

    public EntityExistException(String message) {
        super(message, HttpStatus.BAD_REQUEST.value());
    }
}