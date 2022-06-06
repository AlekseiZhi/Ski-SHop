package ru.skishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skishop.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT distinct u " +
            "FROM User u " +
            "LEFT JOIN FETCH u.roles")
    List<User> findAllUsers();

    User findUserByEmail(String email);
}