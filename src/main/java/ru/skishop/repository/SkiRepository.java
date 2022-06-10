package ru.skishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.skishop.entity.Ski;

@Repository
public interface SkiRepository extends JpaRepository<Ski, Long>, JpaSpecificationExecutor<Ski> {

    Ski findSkisById(Long id);
}