package ru.skishop.criteriaApi;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public abstract class PageableParams {

    @Min(0)
    @NotNull
    protected Integer page;

    @Min(1)
    @NotNull
    protected Integer size;
}