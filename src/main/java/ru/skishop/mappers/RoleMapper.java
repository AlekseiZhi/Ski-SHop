package ru.skishop.mappers;

import org.mapstruct.Mapper;
import ru.skishop.DTO.RoleDto;
import ru.skishop.entities.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDto toRoleDto(Role role);
    Role toRole(RoleDto roleDto);
}