package ru.skishop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.skishop.dto.UserBasketItemDto;
import ru.skishop.service.UserBasketItemService;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/baskets")
public class UserBasketItemController {

    private final UserBasketItemService userBasketItemService;

    @GetMapping("/current")
    @Operation(summary = "Get list of current basket")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Get list of basket "),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    public ResponseEntity<List<UserBasketItemDto>> getBasketForCurrentUser(@RequestParam("page") @Min(0) int page,
                                                                           @RequestParam("size") @Min(1) int size) {
        List<UserBasketItemDto> basketItems = userBasketItemService.getBasketForCurrentUser(page, size);
        return ResponseEntity.ok(basketItems);
    }

    @PostMapping
    @Operation(summary = "Create new entry in the basket")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Create new entry in the basket"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
    })
    public ResponseEntity<UserBasketItemDto> create(@RequestParam("skiId") @Min(1) Long skiId) {
        UserBasketItemDto userBasketItemDto = userBasketItemService.create(skiId);
        return ResponseEntity.ok(userBasketItemDto);
    }

    @PutMapping
    @Operation(summary = "Edit ski amount")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Edit ski amount"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
    })
    public ResponseEntity<UserBasketItemDto> editAmount(@RequestParam("skiId") @Min(1) Long skiId,
                                                        @RequestParam("skiAmount") @Min(1) int skiAmount) {
        UserBasketItemDto userBasketItemDto = userBasketItemService.editSkiAmount(skiId, skiAmount);
        return ResponseEntity.ok(userBasketItemDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete entry in the basket")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Delete entry in the basket"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
    })
    public ResponseEntity<Void> delete(@PathVariable("id") @Min(1) Long skiId) {
        userBasketItemService.delete(skiId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    @Operation(summary = "Delete entry in the basket for current user")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Delete entry in the basket for current user"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
    })
    public ResponseEntity<Void> clearBasketForCurrentUser() {
        userBasketItemService.clearBasketForCurrentUser();
        return ResponseEntity.noContent().build();
    }
}