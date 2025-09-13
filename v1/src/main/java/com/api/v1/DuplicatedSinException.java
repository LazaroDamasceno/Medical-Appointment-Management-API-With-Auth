package com.api.v1;

public class DuplicatedSinException extends RuntimeException {
    public DuplicatedSinException() {
        super("The SIN is already registered.");
    }
}
