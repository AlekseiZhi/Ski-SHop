package ru.skishop.exceptionHandler;

import lombok.Data;

@Data
public class ApiException extends RuntimeException {

    private int httpStatus;

    public ApiException(String message, int httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}