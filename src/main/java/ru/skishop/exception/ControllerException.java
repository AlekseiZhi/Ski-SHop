package ru.skishop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class ControllerException {

    @ExceptionHandler(NotFoundException.class)
    public ErrorMessage notFoundException(NotFoundException exception, WebRequest webRequest) {

        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));
        return message;
    }

    @ExceptionHandler(Exception.class)
    public ErrorMessage globalExceptionHandler(Exception exception, WebRequest webRequest) {

        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));
        return message;
    }
}