package ru.skishop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skishop.service.BuyService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/buy")
public class BuyController {

    private final BuyService buyService;

    @GetMapping("/addbasket/{id}/{amount}")
    public ResponseEntity<Void> getSkisWithCriteria(@PathVariable("id") Long id,
                                                    @PathVariable("amount") int amount) {
        buyService.addBasket(id, amount);
        return ResponseEntity.ok().build();
    }
}
