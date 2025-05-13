package com.api.v1.common;

import lombok.Getter;

@Getter
public enum StatusCode {

    NOT_FOUND(404),
    CONFLICT(409);

    private final int code;

    StatusCode(int code) {
        this.code = code;
    }
}