package ru.skishop.mapper;

import org.mapstruct.Mapper;
import ru.skishop.dto.OrderItemDto;
import ru.skishop.entity.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItemDto toOrderItemDto(OrderItem orderItem);
}