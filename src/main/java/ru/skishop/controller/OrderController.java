package ru.skishop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.skishop.dto.OrderDto;
import ru.skishop.feign.EmailProxy;
import ru.skishop.service.OrderService;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Validated
public class OrderController {

    private final OrderService orderService;
    private final EmailProxy emailProxy;

    @GetMapping
    @Operation(summary = "Get list of current orders")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Get list of orders "),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<List<OrderDto>> getOrderForCurrentUser() {
        List<OrderDto> ordersDto = orderService.getOrderForCurrentUser();
        return ResponseEntity.ok(ordersDto);
    }

    @PostMapping("/buy")
    @Operation(summary = "Create new order and clean basket for current user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Create new order and clean basket for current user"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
    })
    public ResponseEntity<OrderDto> create() {
        OrderDto orderDto = orderService.create();
        return ResponseEntity.ok(orderDto);
    }

    @PutMapping
    @RolesAllowed("admin")
    @Operation(summary = "Edit ski amount in order")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Edit ski amount in order"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
    })
    public ResponseEntity<OrderDto> edit(@RequestParam("orderId") @Min(1) Long orderId,
                                         @RequestParam("skiId") @Min(1) Long skiId,
                                         @RequestParam("skiAmount") @Min(1) int skiAmount) {
        OrderDto orderDto = orderService.edit(orderId, skiId, skiAmount);
        return ResponseEntity.ok(orderDto);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("admin")
    @Operation(summary = "Delete order")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Delete order"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
    })
    public ResponseEntity<Void> delete(@PathVariable("id") @Min(1) Long orderId) {
        orderService.delete(orderId);
        return ResponseEntity.noContent().build();
    }
}