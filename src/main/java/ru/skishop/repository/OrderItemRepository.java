package ru.skishop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skishop.entity.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Modifying
    @Query("UPDATE OrderItem o" +
            " SET o.amount = :skiAmount" +
            " WHERE o.ski.id= :skiId AND o.order.id = :orderId")
    void editSkiAmount(Long orderId, Long skiId, int skiAmount);

    void deleteByOrderId(Long orderId);

    boolean existsOrderItemByOrderId(Long orderId);

    boolean existsOrderItemBySkiId(Long skiId);
}