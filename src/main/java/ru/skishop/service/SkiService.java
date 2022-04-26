package ru.skishop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skishop.dto.SkiDto;
import ru.skishop.entity.Ski;
import ru.skishop.exceptionHandler.NotFoundException;
import ru.skishop.mapper.SkiMapper;
import ru.skishop.repository.SkiRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SkiService {

    private final SkiRepository skiRepository;
    private final SkiMapper skiMapper;

    public List<SkiDto> findAllSkis() {
        return skiRepository.findAll().stream().map(skiMapper::toSkiDto).collect(Collectors.toList());
    }

    public SkiDto create(SkiDto skiDto) {
        Ski ski = skiMapper.toEntity(skiDto);
        ski = skiRepository.save(ski);
        return skiMapper.toSkiDto(ski);
    }

    public SkiDto edit(SkiDto skiDto) {
        if (!skiRepository.existsById(skiDto.getId())) {
            log.info("SkiService: Not found Ski by id = {}", skiDto.getId());
            throw new NotFoundException("Not found Ski by id = " + skiDto.getId());
        }
        Ski ski = skiMapper.toEntity(skiDto);
        ski = skiRepository.save(ski);
        return skiMapper.toSkiDto(ski);
    }

    public void delete(Long id) {
        if (!skiRepository.existsById(id)) {
            log.info("SkiService: Not found Ski by id = {}", id);
            throw new NotFoundException("Not found Ski by id = " + id);
        }
            skiRepository.deleteById(id);
    }

    public SkiDto find(Long id){
        return skiMapper.toSkiDto(skiRepository.findSkisById(id));
    }
}