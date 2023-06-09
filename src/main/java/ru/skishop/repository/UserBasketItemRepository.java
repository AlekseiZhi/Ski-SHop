package ru.skishop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skishop.entity.UserBasketItem;

import java.util.List;

@Repository
public interface UserBasketItemRepository extends JpaRepository<UserBasketItem, Long>, JpaSpecificationExecutor<UserBasketItem> {

    UserBasketItem findUserBasketItemByUserIdAndSkiId(Long userId, Long skiId);

    void deleteUserBasketItemByUserIdAndSkiId(Long userId, Long skiId);

    @Query(value = "select ubi " +
            "from UserBasketItem ubi " +
            "join fetch ubi.ski s " +
            "join fetch ubi.user u " +
            "where u.id = :userId",
            countQuery = "select count(ubi) from UserBasketItem ubi where ubi.user.id = :userId"
    )
    Page<UserBasketItem> findAllByUserId(Long userId, Pageable pageable);

    @Query(value = "select ubi " +
            "from UserBasketItem ubi " +
            "join fetch ubi.ski s " +
            "join fetch ubi.user u " +
            "where u.id = :userId")
    List<UserBasketItem> findAllByUserId(Long userId);

    boolean existsByUserIdAndSkiId(Long userId, Long skiId);

    boolean existsUserBasketItemById(Long userBasketItemId);

    @Modifying
    @Query("UPDATE UserBasketItem u" +
            " SET u.amount = :skiAmount" +
            " WHERE u.ski.id= :skiId")
    void editSkiAmount(Long skiId, int skiAmount);

    void deleteAllByUserId(Long userId);

    UserBasketItem findUserBasketItemById(Long userBasketItemId);
}