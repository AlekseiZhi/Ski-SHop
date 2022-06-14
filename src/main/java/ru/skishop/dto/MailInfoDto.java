package ru.skishop.dto;

import lombok.Data;

@Data
public class MailInfoDto {

    private String mailTo;
    private String mailFrom;
    private String subject;
    private String message;
}
