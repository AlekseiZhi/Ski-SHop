package ru.skishop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private final Integer totalPages;

    public PaginationWrapper(@JsonProperty("data") List<T> data, @JsonProperty("page") int page, @JsonProperty("size") int size, @JsonProperty("totalElements") Long totalElements, @JsonProperty("totalPages") int totalPages) {
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