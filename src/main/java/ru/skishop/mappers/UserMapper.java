package ru.skishop.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skishop.DTO.UserDto;
import ru.skishop.entities.User;

@Mapper(uses = {RoleMapper.class})
public interface UserMapper {

    @Mapping(source = "user.roles", target = "roles")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "listRoleId", ignore = true)
    UserDto toUserDto(User user);

    User toUser(UserDto userDto);
}