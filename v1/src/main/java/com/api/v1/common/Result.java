package com.api.v1.common;

import lombok.Getter;

@Getter
public sealed class Result<T> permits Result20X, Result40X {

    private StatusCode statusCode;
    private HttpMethods httpMethod;

    protected Result() {
    }

    public Result(StatusCode statusCode, HttpMethods httpMethod) {
        this.statusCode = statusCode;
        this.httpMethod = httpMethod;
    }

    public static <T> Result<T> empty() {
        return new Result<>();
    }

}
