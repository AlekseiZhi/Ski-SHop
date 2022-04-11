package ru.skishop.exceptionHandler;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@RequiredArgsConstructor
public class ErrorMessage {

    private final Date timestamp;
    private final String message;
}