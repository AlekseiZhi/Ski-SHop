package ru.skishop.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skishop.DTO.UserDto;
import ru.skishop.entities.User;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {

    @Mapping(source = "user.roles", target = "roles")
    @Mapping(target = "password", ignore = true)
    UserDto toUserDto(User user);

    @Mapping(target = "roles", ignore = true)
    User toUser(UserDto userDto);
}