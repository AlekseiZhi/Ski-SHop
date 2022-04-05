package ru.skishop.mapper;

import org.mapstruct.Mapper;
import ru.skishop.dto.RoleDto;
import ru.skishop.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDto toRoleDto(Role role);
}