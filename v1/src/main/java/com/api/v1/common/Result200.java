package com.api.v1.common;

public final class Result200<T> extends Result {

    private final T body;

    private Result200(int statusCode, T body) {
        super(statusCode);
        this.body = body;
    }

    public static <T> Result200<T> from(T body) {
        return new Result200<>(Constants.STATUS_OK, body);
    }

    public T getBody() {
        return body;
    }
}
