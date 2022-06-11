package ru.skishop.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class MailInfoDto {

    @Email(message = "Email should be valid")
    @NotEmpty(message = "mailTo cannot be empty")
    private String mailTo;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "mailTo cannot be empty")
    private String mailFrom;

    @NotEmpty(message = "subject cannot be empty")
    private String subject;

    @NotEmpty(message = "message cannot be empty")
    private String message;
}
