package ru.skishop.dto;

import lombok.Data;
import ru.skishop.entity.OrderItem;
import ru.skishop.entity.User;

import java.util.Date;
import java.util.List;

@Data
public class OrderDto {

    private Long id;
    private Date date;
    private List<OrderItem> orderItems;
}