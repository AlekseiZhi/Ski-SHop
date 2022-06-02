package ru.skishop.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UserBasketItemDto {

    @NotNull
    private SkiDto skiDto;

    @Min(value = 1)
    private int amount;
}