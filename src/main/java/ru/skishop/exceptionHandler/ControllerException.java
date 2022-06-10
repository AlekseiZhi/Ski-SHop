package ru.skishop.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ControllerException {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ErrorMessage globalExceptionHandler(Exception exception) {

        return new ErrorMessage(new Date(), exception.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorMessage> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {

        List<FieldError> errors = exception.getFieldErrors();
        return errors.stream()
                .map(err -> (new ErrorMessage(new Date(), err.getDefaultMessage())))
                .collect(Collectors.toList());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public List<ErrorMessage> bindExceptionHandler(BindException exception) {

        List<FieldError> errors = exception.getFieldErrors();
        return errors.stream()
                .map(err -> (new ErrorMessage(new Date(), err.getDefaultMessage())))
                .collect(Collectors.toList());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> forbiddenExceptionHandler(AccessDeniedException exception) {
        ErrorMessage message = new ErrorMessage(new Date(), exception.getMessage());
        return ResponseEntity.ok(message);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorMessage> apiException(ApiException exception) {
        ErrorMessage message = new ErrorMessage(new Date(), exception.getMessage());
        return ResponseEntity.status(exception.getHttpStatus()).body(message);
    }
}