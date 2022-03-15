package ru.skishop.dto;

import lombok.Data;

@Data
public class UserForAuthDto {

    private String fullName;
    private String email;
    private String password;
}
