package com.api.v1.common;

public final class Result409Conflict extends Result {

    private final String message;

    private Result409Conflict(int statusCode, String message) {
        super(statusCode);
        this.message = message;
    }

    public static Result409Conflict from(String message) {
        return new Result409Conflict(Constants.STATUS_CONFLICT, message);
    }

    public String getMessage() {
        return message;
    }
}
