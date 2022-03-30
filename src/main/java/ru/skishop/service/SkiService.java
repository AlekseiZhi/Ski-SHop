package ru.skishop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skishop.dto.SkiDto;
import ru.skishop.entities.Ski;
import ru.skishop.mappers.SkiMapper;
import ru.skishop.repository.SkiRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkiService {

    private final SkiRepository skiRepository;
    private final SkiMapper skiMapper;

    public List<SkiDto> findAllSkis() {
        return skiRepository.findAll().stream().map(skiMapper::toSkiDto).collect(Collectors.toList());
    }

    public SkiDto create(SkiDto skiDto) {
        Ski ski = skiMapper.toEntity(skiDto);
        return skiMapper.toSkiDto(skiRepository.save(ski));
    }

    public SkiDto edit(SkiDto skiDto) {
        Ski ski = skiMapper.toEntity(skiDto);
        return skiMapper.toSkiDto(skiRepository.save(ski));
    }

    public void delete(Long id) {
        skiRepository.deleteById(id);
    }
}