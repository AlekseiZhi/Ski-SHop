package ru.skishop.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserBasketItemService userBasketItemService;

    public void create() {

       // basketRepository.save(userBasketSki);
    }

//    public void editAmount(Long skiId, int amount) {
//        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (!basketRepository.existsById(skiId)) {
//            log.info("BasketService: Not found Ski by id = {}", skiId);
//            throw new NotFoundException("Not found Ski by id = " + skiId);
//        }
//        UserBasketSki userBasketSki = basketRepository.findUserBasketSkiByUserIdAndSkiId(userId, skiId);
//        userBasketSki.setAmount(amount);
//        basketRepository.save(userBasketSki);
//    }
//
//    @Transactional
//    public void delete(Long skiId) {
//        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (!basketRepository.existsById(skiId)) {
//            log.info("BasketService: Not found Ski by id = {}", skiId);
//            throw new NotFoundException("Not found Ski by id = " + skiId);
//        }
//        basketRepository.deleteUserBasketSkiByUserIdAndSkiId(userId, skiId);
//    }
}
