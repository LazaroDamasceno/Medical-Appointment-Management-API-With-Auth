package com.api.v1.common;

import lombok.Getter;

@Getter
public final class Result20X<T> extends Result<T> {

    private final T body;
    private static final int OK = 200;
    private static final int CREATED = 201;

    private Result20X(int statusCode, T body) {
        super(statusCode);
        this.body = body;
    }

    public static <T> Result20X<T> generate200(T body) {
        return new Result20X<>(OK, body);
    }

    public static <T> Result20X<T> generate201(T body) {
        return new Result20X<>(CREATED, body);
    }
}
