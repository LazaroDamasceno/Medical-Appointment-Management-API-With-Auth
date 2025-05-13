package com.api.v1.common;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class Result<T> extends RepresentationModel<Result<T>> {

    private ResultStatus status;
    private String message;
    private T body;

    protected Result() {
    }

    protected Result(ResultStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    protected Result(ResultStatus status, T body) {
        this.status = status;
        this.body = body;
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
                ResultStatus.SUCCESSFUL,
                message
        );
    }
}
