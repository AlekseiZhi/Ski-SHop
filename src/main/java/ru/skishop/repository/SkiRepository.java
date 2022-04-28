package ru.skishop.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.skishop.entity.Ski;

public interface SkiRepository extends PagingAndSortingRepository<Ski, Long> {
    Ski findSkisById(Long id);
}