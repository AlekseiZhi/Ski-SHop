package ru.skishop.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PaginationWrapper<T> {

    private final int page;
    private final int size;
    private final List<T> data;
    private final Long totalElements;
    private final Integer currentPage;
    private final Integer nextPage;
    private final Integer prevPage;

    public PaginationWrapper(List<T> data, int page, int size, Long totalElements) {
        this.page = page;
        this.size = size;
        this.data = data;
        this.totalElements = totalElements;
        this.currentPage = page;
        this.nextPage = (page < Math.toIntExact(this.totalElements / size)) ? page + 1 : null;
        this.prevPage = (page - 1 >= 0) ? page - 1 : null;
    }
}