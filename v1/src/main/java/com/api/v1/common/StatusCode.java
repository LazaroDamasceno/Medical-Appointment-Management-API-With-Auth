package com.api.v1.common;

public enum StatusCode {

    NOT_FOUND(404),
    CONFLICT(409);

    private final int code;

    StatusCode(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}