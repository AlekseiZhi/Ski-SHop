package ru.skishop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skishop.entity.OrderItem;
import ru.skishop.entity.UserBasketItem;


@Mapper(componentModel = "spring")
public interface OrderItemFromUserBasketItemMapper {

    @Mapping(target = "id", ignore = true)
    OrderItem toOrderItem(UserBasketItem userBasketItem);
}