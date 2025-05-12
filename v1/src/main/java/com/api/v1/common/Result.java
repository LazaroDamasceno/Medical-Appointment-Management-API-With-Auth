package com.api.v1.common;

import lombok.Getter;

@Getter
public sealed class Result permits Result20X, Result40X {

    private int statusCode;

    protected Result() {
    }

    public Result(int statusCode) {
        this.statusCode = statusCode;
    }

    public static Result empty() {
        return new Result();
    }

}
