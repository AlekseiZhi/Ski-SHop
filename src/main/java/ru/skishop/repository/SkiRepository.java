package ru.skishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skishop.entity.Ski;

public interface SkiRepository extends JpaRepository<Ski, Long> {

    Ski findSkisById(Long id);

}