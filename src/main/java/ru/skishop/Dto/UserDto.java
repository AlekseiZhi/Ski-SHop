package ru.skishop.Dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private Long id;
    private String fullName;
    private String email;
    private String password;
    private List<RoleDto> roles;
}