package com.api.v1.common;

import lombok.Getter;

@Getter
public final class Result40X<T> extends Result<T> {

    private final String message;

    private Result40X(StatusCode statusCode, HttpMethods httpMethod, String message) {
        super(statusCode, httpMethod);
        this.message = message;
    }

    public static <T> Result40X<T> generate404(String message) {
        return new Result40X<>(
                StatusCode.NOT_FOUND_404,
                HttpMethods.GET,
                message
        );
    }

    public static <T> Result40X<T> generate409(HttpMethods httpMethod, String message) {
        return new Result40X<>(
                StatusCode.CONFLICT_409,
                httpMethod,
                message
        );
    }
}
