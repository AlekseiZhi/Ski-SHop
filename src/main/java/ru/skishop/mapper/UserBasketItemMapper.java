package ru.skishop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skishop.dto.UserBasketItemDto;
import ru.skishop.entity.UserBasketItem;

@Mapper(componentModel = "spring")
public interface UserBasketItemMapper {

    @Mapping(source = "userBasketItem.ski", target = "ski")
    UserBasketItemDto toUserBasketItemDto(UserBasketItem userBasketItem);
}