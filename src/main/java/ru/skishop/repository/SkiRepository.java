package ru.skishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.skishop.entity.Ski;

public interface SkiRepository extends JpaRepository<Ski, Long>, JpaSpecificationExecutor<Ski> {

    Ski findSkisById(Long id);
}