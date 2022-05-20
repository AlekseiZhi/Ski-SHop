package ru.skishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skishop.entity.UserBasketSki;

@Repository
public interface UserBasketSkiRepository extends JpaRepository<UserBasketSki, Long> {

}
