package com.api.v1.common;

import java.util.List;

public final class PaginationResult<T> {

    private final List<T> items;
    private final long total;

    private PaginationResult(List<T> items, long total) {
        this.items = items;
        this.total = total;
    }

    public static <T> PaginationResult<T> of(List<T> items, long total) {
        return new PaginationResult<>(items, total);
    }

    public List<T> getItems() {
        return items;
    }

    public long getTotal() {
        return total;
    }
}
