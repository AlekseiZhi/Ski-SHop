package ru.skishop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import ru.skishop.repository.UserBasketItemRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserBasketItemService {

    private final UserBasketItemRepository userBasketItemRepository;
    private final UserBasketItemMapper userBasketItemMapper;
    private final CurrentUser currentUser;

    public List<UserBasketItemDto> getBasketForCurrentUser(int page, int size) {
        Long userId = currentUser.getId();
        Pageable paging = PageRequest.of(page, size);
        Page<UserBasketItem> pagedResult = userBasketItemRepository.findAllByUserId(userId, paging);
        return pagedResult.getContent().stream().map(userBasketItemMapper::toUserBasketItemDto).collect(Collectors.toList());
    }

    public UserBasketItemDto create(Long skiId) {
        Long userId = currentUser.getId();
        if (userBasketItemRepository.existsByUserIdAndSkiId(userId, skiId)) {
            log.info("UserBasketItemService: entry with skiId = {} number already exists", skiId);
            throw new EntityExistException("Entry already exists");
        }
            UserBasketItem userBasketItem = new UserBasketItem();
            userBasketItem.setSki(new Ski(skiId));
            userBasketItem.setAmount(1);
            userBasketItem.setUser(new User(userId));
            userBasketItem = userBasketItemRepository.save(userBasketItem);
            return userBasketItemMapper.toUserBasketItemDto(userBasketItem);
    }

    @Transactional
    public UserBasketItemDto editSkiAmount(Long skiId, int skiAmount) {
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

    @Transactional
    public void clearBasketForCurrentUser() {
        Long userId = currentUser.getId();
        userBasketItemRepository.deleteAllByUserId(userId);
    }

    public UserBasketItemDto getUserBasketItem(Long userId, Long skiId){
      UserBasketItem userBasketItem = userBasketItemRepository.findUserBasketItemByUserIdAndSkiId(userId, skiId);
      return userBasketItemMapper.toUserBasketItemDto(userBasketItem);
    }
}