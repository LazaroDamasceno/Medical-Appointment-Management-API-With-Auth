package com.api.v1.common;

public final class Result404 extends Result {

    private final String message;

    private Result404(int statusCode, String message) {
        super(statusCode);
        this.message = message;
    }

    public static Result404 from(String message) {
        return new Result404(Constants.STATUS_NOT_FOUND, message);
    }

    public String getMessage() {
        return message;
    }
}
