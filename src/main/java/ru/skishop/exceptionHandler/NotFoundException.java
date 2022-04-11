package ru.skishop.exceptionHandler;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String msg) {
        super(msg);
    }
}