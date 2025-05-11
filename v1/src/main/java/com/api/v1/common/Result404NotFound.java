package com.api.v1.common;

public final class Result404NotFound extends Result {

    private final String message;

    private Result404NotFound(int statusCode, String message) {
        super(statusCode);
        this.message = message;
    }

    public static Result404NotFound from(String message) {
        return new Result404NotFound(Constants.STATUS_NOT_FOUND, message);
    }

    public String getMessage() {
        return message;
    }
}
