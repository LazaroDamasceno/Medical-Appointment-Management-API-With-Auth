package com.api.v1.common;

import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public sealed class Result<T> extends RepresentationModel<Result<T>> permits Result20X, Result40X {

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
