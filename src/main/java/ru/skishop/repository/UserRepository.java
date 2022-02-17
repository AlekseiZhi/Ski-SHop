package ru.skishop.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skishop.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
