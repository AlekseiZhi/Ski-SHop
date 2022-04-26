package ru.skishop.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UserDto {

    @Min(value = 1)
    private Long id;

    @NotEmpty(message = "Name cannot be empty")
    private String fullName;

    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @NotNull
    private List<RoleDto> roles;
}