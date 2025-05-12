package com.api.v1.common;

import lombok.Getter;

@Getter
public final class Result20X<T> extends Result<T> {

    private final T body;

    private Result20X(StatusCode statusCode, HttpMethods httpMethod, T body) {
        super(statusCode, httpMethod);
        this.body = body;
    }

    public static <T> Result20X<T> generate200(T body) {
        return new Result20X<>(
                StatusCode.OK_200,
                HttpMethods.GET,
                body
        );
    }

    public static <T> Result20X<T> generate201(T body) {
        return new Result20X<>(
                StatusCode.CREATED_201,
                HttpMethods.GET,
                body
        );
    }
}
