package ru.skishop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skishop.dto.OrderItemDto;
import ru.skishop.entity.OrderItem;
import ru.skishop.entity.UserBasketItem;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItemDto toOrderItemDto(OrderItem orderItem);

    @Mapping(target = "id", ignore = true)
    OrderItem toOrderItem(UserBasketItem userBasketItem);
}