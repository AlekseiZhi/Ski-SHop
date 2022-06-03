package ru.skishop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skishop.dto.OrderDto;
import ru.skishop.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/buy")
    public ResponseEntity<OrderDto> create() {
    OrderDto orderDto = orderService.create();
        return ResponseEntity.ok(orderDto);
    }

    @GetMapping("")
    public ResponseEntity<OrderDto> getOrderForCurrentUser() {
    OrderDto orderDto = orderService.create();
        return ResponseEntity.ok(orderDto);
    }


}