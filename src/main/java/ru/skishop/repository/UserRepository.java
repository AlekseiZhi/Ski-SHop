package ru.skishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skishop.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}