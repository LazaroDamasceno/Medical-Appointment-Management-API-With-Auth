package com.api.v1.common;

import lombok.Getter;

@Getter
public final class Result<T> {

    private ResultStatus status;
    private String message;
    private T body;

    private Result() {}

    private Result(ResultStatus status, T body) {
        this.status = status;
        this.message = "Successful operation.";
        this.body = body;
    }

    private Result(ResultStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public static Result<Void> empty() {
        return new Result<>();
    }

    public static <T>  Result<T> success(T body) {
        return new Result<>(
                ResultStatus.SUCCESSFUL,
                body
        );
    }

    public static <T>  Result<T> error(String message) {
        return new Result<>(
                ResultStatus.ERROR,
                message
        );
    }
}
