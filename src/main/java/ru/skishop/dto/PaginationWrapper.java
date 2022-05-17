package ru.skishop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PaginationWrapper<T> {

    private int page;
    private int size;
    private List<T> data;
    private Long totalElements;
    private Integer currentPage;
    private Integer nextPage;
    private Integer prevPage;
    private Integer totalPages;

    public PaginationWrapper(List<T> data, int page, int size, Long totalElements, int totalPages) {
        this.page = page;
        this.size = size;
        this.data = data;
        this.totalElements = totalElements;
        this.currentPage = page;
        this.totalPages = totalPages;
        this.nextPage = (page < totalPages - 1 ? page + 1 : null);
        this.prevPage = (page - 1 >= 0) ? page - 1 : null;
    }
}