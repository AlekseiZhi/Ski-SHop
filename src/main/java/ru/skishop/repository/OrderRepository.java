package ru.skishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skishop.entity.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT DISTINCT ord " +
            "FROM Order ord " +
            "JOIN FETCH ord.orderItemList ori " +
            "WHERE ord.user.id = :userId")
    List<Order> findAllByUserId(Long userId);
}