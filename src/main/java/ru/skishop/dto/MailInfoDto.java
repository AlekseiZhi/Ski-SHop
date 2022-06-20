package ru.skishop.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MailInfoDto implements Serializable {

    private String mailTo;
    private String mailFrom;
    private String subject;
    private String message;
}
