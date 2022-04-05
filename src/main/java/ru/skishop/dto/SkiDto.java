package ru.skishop.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
public class SkiDto {

    @Min(value = 1)
    private Long id;

    @NotEmpty(message = "Category cannot be empty")
    private String category;

    @NotEmpty(message = "Company cannot be empty")
    private String company;

    @NotEmpty(message = "Title cannot be empty")
    private String title;

    @Min(value = 50, message = "Length should not be less than 50")
    @Max(value = 205, message = "Length should not be greater than 205")
    private int length;

    @Min(value = 50, message = "Length should not be less than 50")
    @Max(value = 9999999, message = "Length should not be greater than 9999999")
    private BigDecimal price;
}
