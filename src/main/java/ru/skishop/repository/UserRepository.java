package ru.skishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skishop.entities.User;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT distinct u " +
            "FROM User u " +
            "LEFT JOIN FETCH u.roles")
    List<User> findAllUsers();
}