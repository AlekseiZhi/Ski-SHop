package ru.skishop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skishop.entity.OrderItem;
import ru.skishop.repository.OrderItemRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItem create(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
