package ru.skishop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skishop.dto.OrderDto;
import ru.skishop.dto.OrderItemDto;
import ru.skishop.entity.*;
import ru.skishop.mapper.OrderCustomMapper;
import ru.skishop.mapper.OrderItemFromUserBasketItemMapper;
import ru.skishop.mapper.OrderItemMapper;
import ru.skishop.repository.OrderRepository;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final UserBasketItemService userBasketItemService;
    private final CurrentUser currentUser;
    private final OrderRepository orderRepository;
    private final OrderItemFromUserBasketItemMapper orderItemFromUserBasketItemMapper;
    private final OrderItemService orderItemService;
    private final OrderCustomMapper orderCustomMapper;
    private final OrderItemMapper orderItemMapper;

    public OrderDto create() {
        Long userId = currentUser.getId();
        List<UserBasketItem> userBasketItemDtoList = userBasketItemService.getBasketForCurrentUser();
        List<OrderItem> orderItemList = userBasketItemDtoList.stream().map(orderItemFromUserBasketItemMapper::toOrderItem).collect(Collectors.toList());
        orderItemList.forEach(orderItemService::create);
        List<OrderItemDto> orderItemDtoList = orderItemList.stream().map(orderItemMapper::toOrderItemDto).collect(Collectors.toList());
        Order order = new Order();
        order.setDate(Instant.now());
        order.setUser(new User(userId));
        order = orderRepository.save(order);
        return orderCustomMapper.toOrderDto(order, orderItemDtoList);

    }
}