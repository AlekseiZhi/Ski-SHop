package ru.skishop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skishop.dto.SkiDto;
import ru.skishop.entities.Ski;
import ru.skishop.exception.NotFoundException;
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
        List<SkiDto> skis = skiRepository.findAll().stream().map(skiMapper::toSkiDto).collect(Collectors.toList());
        if (skis.isEmpty()) {
            throw new NotFoundException("Not found skis");
        }
        return skis;
    }

    public SkiDto create(SkiDto skiDto) {
        Ski ski = skiMapper.toEntity(skiDto);
        ski = skiRepository.save(ski);
        return skiMapper.toSkiDto(ski);
    }

    public SkiDto edit(SkiDto skiDto) {
        Ski ski = skiMapper.toEntity(skiDto);
        ski = skiRepository.save(ski);
        return skiMapper.toSkiDto(ski);
    }

    public void delete(Long id) {
        skiRepository.deleteById(id);
    }
}