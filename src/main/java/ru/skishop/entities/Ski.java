package ru.skishop.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "skis")
public class Ski {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categories;
    private String company;
    private String title;
    private int length;
    private BigDecimal price;
}
