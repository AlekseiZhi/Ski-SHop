package ru.skishop.DTO;

import lombok.Data;

@Data
public class UserDtoForAuth {

    private String fullName;
    private String email;
    private String password;
}
