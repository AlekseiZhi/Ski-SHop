package ru.skishop.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class UserBasketItemDto {

    private SkiDto skiDto;

    @Min(value = 1)
    private int amount;
}