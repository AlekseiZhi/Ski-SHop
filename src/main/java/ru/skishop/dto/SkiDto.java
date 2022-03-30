package ru.skishop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SkiDto {

    private Long id;
    private String category;
    private String company;
    private String title;
    private int length;
    private BigDecimal price;
}
