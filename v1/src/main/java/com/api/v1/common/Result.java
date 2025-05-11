package com.api.v1.common;

public sealed class Result permits Result200Ok, Result201Created, Result404NotFound, Result409Conflict {

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
