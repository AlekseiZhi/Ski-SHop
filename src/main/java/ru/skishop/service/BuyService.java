package ru.skishop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skishop.entity.Ski;
import ru.skishop.entity.UserBasketSki;
import ru.skishop.repository.SkiRepository;
import ru.skishop.repository.UserBasketSkiRepository;
import ru.skishop.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class BuyService {

    private final UserBasketSkiRepository userBasketSkiRepository;
    private final SkiRepository skiRepository;
    private final UserRepository userRepository;

    public void addBasket(Long id, int amount) {

        Ski ski = skiRepository.findSkisById(id);
        UserBasketSki userBasketSki = new UserBasketSki();
        userBasketSki.setSki(ski);
        userBasketSki.setAmount(amount);
        userBasketSki.setUser(userRepository.getById(id));
        userBasketSkiRepository.save(userBasketSki);

    }
}
