package com.api.v1.common;

public class Result {

    private String statusCode;

    private Result() {
    }

    protected Result(String statusCode) {
        this.statusCode = statusCode;
    }

    public static Result empty() {
        return new Result();
    }

    public String getStatusCode() {
        return statusCode;
    }
}
