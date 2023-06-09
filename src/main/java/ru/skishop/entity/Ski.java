package ru.skishop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Ski {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private String company;
    private String title;
    private int length;
    private BigDecimal price;

    public Ski(Long skiId) {
        this.id = skiId;
    }
}
