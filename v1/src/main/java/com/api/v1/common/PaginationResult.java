package com.api.v1.common;

import java.util.List;

public final class PaginationResult<T> {

    private List<T> items;
    private long size;

    public PaginationResult() {
    }

    private PaginationResult(List<T> items, long size) {
        this.items = items;
        this.size = size;
    }

    public static <T> PaginationResult<T> of(List<T> items, long size) {
        return new PaginationResult<>(items, size);
    }

    public List<T> getItems() {
        return items;
    }

    public long getSize() {
        return size;
    }
}
