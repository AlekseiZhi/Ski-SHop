package ru.skishop.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class ControllerException {

    @ExceptionHandler(NotFoundException.class)
    public ErrorMessage notFoundException(NotFoundException exception, WebRequest webRequest) {

        ErrorMessage message = new ErrorMessage(
                new Date(),
                exception.getMessage());
        return message;
    }

    @ExceptionHandler(Exception.class)
    public ErrorMessage globalExceptionHandler(Exception exception, WebRequest webRequest) {

        ErrorMessage message = new ErrorMessage(
                new Date(),
                exception.getMessage());
        return message;
    }
}