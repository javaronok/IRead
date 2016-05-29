package com.iread.utils;

import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;

public final class ResultSet<T> {
    private final List<T> rows;
    private final Integer totalPages;
    private final Long totalRecords;

    public ResultSet(Page<T> page) {
        this(page.getContent(), page.getTotalElements(), page.getTotalPages());
    }

    public ResultSet(List<T> rows, Long totalRecords, Integer totalPages) {
        this.rows = rows;
        this.totalRecords = totalRecords;
        this.totalPages = totalPages;
    }

    public List<T> getRows() {
        return Collections.unmodifiableList(rows);
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Long getTotalRecords() {
        return totalRecords;
    }
}