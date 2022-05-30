//package ru.skishop.entity;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//
//@Entity
//@Getter
//@Setter
//public class UserBasketSkiItem {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_basket_ski_id")
//    private UserBasketItem userBasketItem;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ski_id")
//    private Ski ski;
//
//    private int amount;
//}
