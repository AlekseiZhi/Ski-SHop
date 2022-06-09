package ru.skishop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skishop.entity.OrderItem;
import ru.skishop.exceptionHandler.NotFoundException;
import ru.skishop.repository.OrderItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Transactional
    public OrderItem create(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Transactional
    public List<OrderItem> saveAll(List<OrderItem> orderItemList) {
        return orderItemRepository.saveAll(orderItemList);
    }

    @Transactional
    public void edit(Long orderId, Long skiId, int skiAmount) {
        if (!orderItemRepository.existsOrderItemByOrderId(orderId)) {
            log.info("OrderItemService: Not found orderItem by orderId = {}", orderId);
            throw new NotFoundException("Not found orderItem by orderId = " + orderId);
        }
        if (!orderItemRepository.existsOrderItemBySkiId(skiId)) {
            log.info("OrderItemService: Not found orderItem by skiId = {}", skiId);
            throw new NotFoundException("Not found orderItem by skiId = " + skiId);
        }
        orderItemRepository.editSkiAmount(orderId, skiId, skiAmount);
    }

    @Transactional
    public void delete(Long orderId) {
        if (!orderItemRepository.existsById(orderId)) {
            log.info("OrderItemService: Not found orderItem by orderId = {}", orderId);
            throw new NotFoundException("Not found orderItem by orderId = " + orderId);
        }
        orderItemRepository.deleteByOrderId(orderId);
    }
}