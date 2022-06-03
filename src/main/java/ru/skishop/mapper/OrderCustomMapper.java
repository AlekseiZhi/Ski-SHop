package ru.skishop.mapper;

import org.springframework.stereotype.Component;
import ru.skishop.dto.OrderDto;
import ru.skishop.dto.OrderItemDto;
import ru.skishop.entity.Order;

import java.util.List;

@Component
public class OrderCustomMapper {

    public OrderDto toOrderDto(Order order, List<OrderItemDto> orderItemDtoList) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setDate(order.getDate());
        orderDto.setOrderItemDtoList(orderItemDtoList);
        return orderDto;
    }
}