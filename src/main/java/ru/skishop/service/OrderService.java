package ru.skishop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skishop.dto.OrderDto;
import ru.skishop.dto.OrderItemDto;
import ru.skishop.entity.*;
import ru.skishop.exceptionHandler.NotFoundException;
import ru.skishop.mapper.OrderItemMapper;
import ru.skishop.mapper.OrderMapper;
import ru.skishop.repository.OrderRepository;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final UserBasketItemService userBasketItemService;
    private final OrderItemService orderItemService;
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;
    private final CurrentUser currentUser;
    private final OrderRepository orderRepository;

    public List<OrderDto> getOrderForCurrentUser() {
        Long userId = currentUser.getId();

        List<Order> orderList = orderRepository.findAllByUserId(userId);
        return orderList.stream().map(orderMapper::toOrderDto).collect(Collectors.toList());
    }

    public OrderDto findOrderById(Long orderId) {
        Order order = orderRepository.findOrderById(orderId);
        return orderMapper.toOrderDto(order);
    }

    @Transactional
    public OrderDto create() {
        Long userId = currentUser.getId();

        List<UserBasketItem> userBasketItemDtoList = userBasketItemService.getBasketForCurrentUser();
        if (userBasketItemDtoList.isEmpty()) {
            log.info("OrderService: Basket is empty");
            throw new NotFoundException("Basket is empty for userId = " + userId);
        }
        List<OrderItem> orderItemList = userBasketItemDtoList.stream().map(orderItemMapper::toOrderItem).collect(Collectors.toList());

        Order order = new Order();
        order.setDate(Instant.now());
        order.setUser(new User(userId));
        order = orderRepository.save(order);

        Order finalOrder = order;
        orderItemList.forEach(orderItem -> orderItem.setOrder(new Order(finalOrder.getId())));

        orderItemService.saveAll(orderItemList);
        List<OrderItemDto> orderItemDtoList = orderItemList.stream().map(orderItemMapper::toOrderItemDto).collect(Collectors.toList());

        OrderDto orderDto = orderMapper.toOrderDto(order);
        orderDto.setItems(orderItemDtoList);

        userBasketItemService.clearBasketForCurrentUser();

        return orderDto;
    }

    @Transactional
    public OrderDto edit(Long orderId, Long skiId, int skiAmount) {
        if (!orderRepository.existsById(orderId)) {
            log.info("OrderService: Not found order by id = {}", orderId);
            throw new NotFoundException("Not found order by id = " + orderId);
        }
        orderItemService.edit(orderId, skiId, skiAmount);
        Order order = orderRepository.getById(orderId);
        return orderMapper.toOrderDto(order);
    }

    @Transactional
    public void delete(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            log.info("OrderService: Not found order by id = {}", orderId);
            throw new NotFoundException("Not found order by id = " + orderId);
        }
        orderItemService.delete(orderId);
        orderRepository.deleteById(orderId);
    }
}