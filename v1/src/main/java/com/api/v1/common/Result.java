package com.api.v1.common;

public final class Result<T> {

    private ResultStatus status;
    private String message;
    private T body;

    private Result() {}

    private Result(ResultStatus status, String message, T body) {
        this.status = status;
        this.message = message;
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
                "Successful operation.",
                body
        );
    }

    public static <T>  Result<T> created(T body) {
        return new Result<>(
                ResultStatus.SUCCESSFUL,
                "Resource created.",
                body
        );
    }

    public static <T>  Result<T> error(String message) {
        return new Result<>(
                ResultStatus.ERROR,
                message
        );
    }

    public ResultStatus status() {
        return status;
    }

    public String message() {
        return message;
    }

    public T body() {
        return body;
    }
}
