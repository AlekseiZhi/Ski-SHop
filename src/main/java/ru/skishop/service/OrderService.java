package ru.skishop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skishop.dto.UserBasketItemDto;
import ru.skishop.entity.CurrentUser;
import ru.skishop.entity.Ski;
import ru.skishop.entity.User;
import ru.skishop.entity.UserBasketItem;
import ru.skishop.exceptionHandler.EntityExistException;
import ru.skishop.exceptionHandler.NotFoundException;
import ru.skishop.mapper.UserBasketItemMapper;
import ru.skishop.repository.OrderRepository;
import ru.skishop.repository.UserBasketItemRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final UserBasketItemRepository userBasketItemRepository;
    private final UserBasketItemMapper userBasketItemMapper;
    private final CurrentUser currentUser;
    private final OrderRepository orderRepository;

//    public UserBasketItemDto create(Long skiId) {
//        Long userId = currentUser.getId();
//         orderService.
//    }

    @Transactional
    public UserBasketItemDto edit(Long skiId, int skiAmount) {
        Long userId = currentUser.getId();
        if (!userBasketItemRepository.existsByUserIdAndSkiId(userId, skiId)) {
            log.info("UserBasketItemService: Not found Ski by {}", skiId);
            throw new NotFoundException("Not found Ski by id = " + skiId, 404);
        }
        userBasketItemRepository.editSkiAmount(skiId, skiAmount);
        UserBasketItem userBasketItem = userBasketItemRepository.findUserBasketItemByUserIdAndSkiId(userId, skiId);
        return userBasketItemMapper.toUserBasketItemDto(userBasketItem);
    }

    @Transactional
    public void delete(Long skiId) {
        Long userId = currentUser.getId();
        if (!userBasketItemRepository.existsByUserIdAndSkiId(userId, skiId)) {
            log.info("UserBasketItemService: Not found Ski by {}", skiId);
            throw new NotFoundException("Not found Ski by id = " + skiId);
        }
        userBasketItemRepository.deleteUserBasketItemByUserIdAndSkiId(userId, skiId);
    }
}