package ru.skishop.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ControllerException {

    @ExceptionHandler(NotFoundException.class)
    public ErrorMessage notFoundException(NotFoundException exception) {

        return new ErrorMessage(new Date(), exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ErrorMessage globalExceptionHandler(Exception exception) {

        return new ErrorMessage(new Date(), exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorMessage> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {

        List<FieldError> errors = exception.getFieldErrors();
        List<ErrorMessage> errorMessageList = new ArrayList<>();

        for (FieldError error : errors) {
            errorMessageList.add(new ErrorMessage(new Date(), error.getDefaultMessage()));
        }
        return errorMessageList;
    }

    @ExceptionHandler(BindException.class)
    public List<ErrorMessage> bindExceptionHandler(BindException exception) {

        List<FieldError> errors = exception.getFieldErrors();
        List<ErrorMessage> errorMessageList = new ArrayList<>();

        for (FieldError error : errors) {
            errorMessageList.add(new ErrorMessage(new Date(), error.getDefaultMessage()));
        }
        return errorMessageList;
    }
}