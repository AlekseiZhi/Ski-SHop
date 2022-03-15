package ru.skishop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenWrapperDto {

    private String accessToken;
}
