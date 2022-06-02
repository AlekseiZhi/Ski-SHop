package ru.skishop.exceptionHandler;

public class NotFoundException extends ApiException {

    public NotFoundException(String msg, int httpStatus) {
        super(msg, httpStatus);
    }

    public NotFoundException(String msg) {
        super(msg, 404);
    }
}