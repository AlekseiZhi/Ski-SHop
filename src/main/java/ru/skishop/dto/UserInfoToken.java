package ru.skishop.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserInfoToken {

    private Long id;
    private String email;
    private List<String> roles;
}
