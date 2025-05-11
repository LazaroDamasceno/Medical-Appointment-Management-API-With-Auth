package com.api.v1.common;

public final class Result200OK extends Result {

    private final String message;

    private Result200OK(String statusCode, String message) {
        super(statusCode);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
