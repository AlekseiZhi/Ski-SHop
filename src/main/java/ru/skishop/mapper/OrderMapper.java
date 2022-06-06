package ru.skishop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skishop.dto.OrderDto;
import ru.skishop.entity.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "order.orderItemList", target = "items")
    OrderDto toOrderDto(Order order);
}