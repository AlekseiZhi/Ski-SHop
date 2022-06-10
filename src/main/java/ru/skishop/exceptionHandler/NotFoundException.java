package ru.skishop.exceptionHandler;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {

    public NotFoundException(String msg) {
        super(msg, HttpStatus.NOT_FOUND.value());
    }
}