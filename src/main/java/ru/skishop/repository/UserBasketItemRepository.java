package ru.skishop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.skishop.entity.UserBasketItem;

import java.util.List;

@Repository
public interface UserBasketItemRepository extends JpaRepository<UserBasketItem, Long>, JpaSpecificationExecutor<UserBasketItem> {

    UserBasketItem findUserBasketItemByUserIdAndSkiId(Long userId, Long skiId);

    void deleteUserBasketItemByUserIdAndSkiId(Long userId, Long skiId);

   Page<UserBasketItem> findAllByUserId(Long id, Pageable pageable);

//    @Query(value = "SELECT distinct u " +
//            "FROM UserBasketItem u " +
//            "LEFT JOIN FETCH u.ski where u.id = :id")

  //  List<UserBasketItem> findAllByUserId(Long id);

    boolean existsByUserIdAndSkiId(Long userId, Long skiId);

    @Modifying
    @Query("UPDATE UserBasketItem u " +
            "SET u.amount = :skiAmount" +
            " WHERE u.ski.id= :skiId")
    int editSkiAmount(Long skiId, int skiAmount);

    void deleteAllByUserId(Long userId);


//
//    @Modifying
//    @Query(value = "UPDATE UserBasketItem u " +
//            "SET u.amount = :skiAmount" +
//            " WHERE u.ski = (select r from Ski r join fetch r where r.id = :id)")
//    void editSkiAmount(@Param("id") Long skiId, @Param("skiAmount") int skiAmount);
}

//
//    UPDATE Team t SET t.current = :current
//        WHERE t.id in (select t1.id from Team t1  LEFT JOIN t1.members m WHERE t1.current = :current_true AND m.account = :account)