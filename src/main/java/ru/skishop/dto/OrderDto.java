package ru.skishop.dto;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class OrderDto {

    private Long id;
    private Instant date;
    private List<OrderItemDto> items;
}