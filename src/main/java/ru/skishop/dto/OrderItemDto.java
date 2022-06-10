package ru.skishop.dto;

import lombok.Data;

@Data
public class OrderItemDto {

    private SkiDto ski;
    private int amount;
}
