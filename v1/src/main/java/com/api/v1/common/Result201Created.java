package com.api.v1.common;

public class Result201Created<T> extends Result {

    private final T body;

    private Result201Created(int statusCode, T body) {
        super(statusCode);
        this.body = body;
    }

    public static <T> Result201Created<T> from(T body) {
        return new Result201Created<>(Constants.STATUS_CREATED, body);
    }

    public T getBody() {
        return body;
    }

}
