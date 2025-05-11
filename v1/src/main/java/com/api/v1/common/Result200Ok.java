package com.api.v1.common;

import lombok.Getter;

@Getter
public final class Result200Ok<T> extends Result {

    private final T body;

    private Result200Ok(int statusCode, T body) {
        super(statusCode);
        this.body = body;
    }

    public static <T> Result200Ok<T> from(T body) {
        return new Result200Ok<>(Constants.STATUS_OK, body);
    }

}
