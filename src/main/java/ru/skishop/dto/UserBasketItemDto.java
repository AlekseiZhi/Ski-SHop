package ru.skishop.dto;

import lombok.Data;

@Data
public class UserBasketItemDto {

    private SkiDto skiDto;
    private int amount;
}