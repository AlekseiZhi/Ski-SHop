package ru.skishop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.orm.hibernate5.SpringSessionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import ru.skishop.dto.UserBasketItemDto;
import ru.skishop.entity.CurrentUser;
import ru.skishop.entity.Ski;
import ru.skishop.entity.User;
import ru.skishop.entity.UserBasketItem;
import ru.skishop.mapper.SkiMapper;
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

//    public List<UserBasketItemDto> getBasketForCurrentUser() {
//        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        List<UserBasketItem> userBasketItems = userBasketItemRepository.findAllByUserId(userId);
//        return userBasketItems.stream().map(userBasketItemMapper::toUserBasketItemDto).collect(Collectors.toList());
//    }

    public List<UserBasketItemDto> getBasketForCurrentUser(int page, int size) {
       // Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = currentUser.getId();
        Pageable paging = PageRequest.of(page, size);
        Page<UserBasketItem> pagedResult = userBasketItemRepository.findAllByUserId(userId, paging);
        return pagedResult.getContent().stream().map(userBasketItemMapper::toUserBasketItemDto).collect(Collectors.toList());
    }

    public UserBasketItemDto create(Long skiId) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userBasketItemRepository.existsByUserIdAndSkiId(userId, skiId)) {
            log.info("UserBasketItemService: this entry already exists");
            return null;
        } else {
            UserBasketItem userBasketItem = new UserBasketItem();
            userBasketItem.setSki(new Ski(skiId));
            userBasketItem.setAmount(1);
            userBasketItem.setUser(new User(userId));
            UserBasketItem userBasketItem1 = userBasketItemRepository.save(userBasketItem);
            return userBasketItemMapper.toUserBasketItemDto(userBasketItem1);
        }
    }


    public void editSkiAmount(Long skiId, int skiAmount) {
      userBasketItemRepository.editSkiAmount(skiId, skiAmount);
    }

    @Transactional
    public void delete(Long skiId) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userBasketItemRepository.deleteUserBasketItemByUserIdAndSkiId(userId, skiId);
    }

    @Transactional
    public void clearDb() {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userBasketItemRepository.deleteAllByUserId(userId);
    }

//    public UserBasketItemDto findByUserIdAndSkiId(Long userId, Long skiId){
//        findByUserIdAndSkiId()
//    }
}