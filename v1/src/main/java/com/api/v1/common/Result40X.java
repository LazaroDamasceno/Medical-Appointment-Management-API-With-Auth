package com.api.v1.common;

import lombok.Getter;

@Getter
public final class Result40X extends Result {

    private final String message;
    private static final int NOT_FOUND = 404;
    private static final int CONFLICT = 409;

    private Result40X(int statusCode, String message) {
        super(statusCode);
        this.message = message;
    }

    public static Result40X generate404(String message) {
        return new Result40X(NOT_FOUND, message);
    }

    public static Result40X generate409(String message) {
        return new Result40X(CONFLICT, message);
    }
}
