package ru.skishop.criteriaApi;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SkiPageableFilter extends PageableParams {

    private String title;
    private String category;
    private String company;
    private Integer priceFrom;
    private Integer priceTo;
    private Integer lengthFrom;
    private Integer lengthTo;
}