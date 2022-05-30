package ru.skishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skishop.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
