package ru.skishop.mapper;

import org.mapstruct.Mapper;
import ru.skishop.dto.SkiDto;
import ru.skishop.entity.Ski;

@Mapper(componentModel = "spring")
public interface SkiMapper {

    SkiDto toSkiDto(Ski ski);

    Ski toEntity(SkiDto skiDto);
}
