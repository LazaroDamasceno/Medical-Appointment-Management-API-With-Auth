package com.api.v1.common;

import lombok.Getter;

@Getter
public final class Result<T, E> {

    private int statusCode;
    private HttpMethods httpMethod;
    private T body;
    private String errorMessage;

    private Result() {
    }

    private Result(int statusCode, HttpMethods httpMethod, T body) {
        this.statusCode = statusCode;
        this.httpMethod = httpMethod;
        this.body = body;
    }

    private Result(int statusCode, HttpMethods httpMethod, String errorMessage) {
        this.statusCode = statusCode;
        this.httpMethod = httpMethod;
        this.errorMessage = errorMessage;
    }

    public static <T, E> Result<T, E> generate200(T body) {
        return new Result<>(
                StatusCodes.OK,
                HttpMethods.GET,
                body
        );
    }

    public static <T, E> Result<T, E> generate201(T body) {
        return new Result<>(
                StatusCodes.CREATED,
                HttpMethods.POST,
                body
        );
    }

    public static <T, E> Result<T, E> generate404(String message) {
        return new Result<>(
                StatusCodes.NOT_FOUND,
                HttpMethods.GET,
                message
        );
    }

    public static <T, E> Result<T, E> generate409WithPost(String message) {
        return new Result<>(
                StatusCodes.CONFLICT,
                HttpMethods.POST,
                message
        );
    }

    public static <T, E> Result<T, E> generate409WithPatch(String message) {
        return new Result<>(
                StatusCodes.CONFLICT,
                HttpMethods.PATCH,
                message
        );
    }
}
