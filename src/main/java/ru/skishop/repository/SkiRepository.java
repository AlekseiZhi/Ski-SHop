package ru.skishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skishop.entities.Ski;

public interface SkiRepository extends JpaRepository<Ski, Long> {

}
