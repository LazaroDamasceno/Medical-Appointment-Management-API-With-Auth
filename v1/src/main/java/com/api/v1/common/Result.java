package com.api.v1.common;

public class Result {

    private int statusCode;

    protected Result() {
    }

    protected Result(int statusCode) {
        this.statusCode = statusCode;
    }

    public static Result empty() {
        return new Result();
    }

    public int getStatusCode() {
        return statusCode;
    }
}
