package ru.skishop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skishop.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/{basketId}")
    public ResponseEntity<Void> create(@PathVariable("basketId") Long basketId) {

        return ResponseEntity.ok().build();
    }
}