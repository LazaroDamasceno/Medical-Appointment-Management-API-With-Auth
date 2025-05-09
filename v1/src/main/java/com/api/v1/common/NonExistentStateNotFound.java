package com.api.v1.common;

public class NonExistentStateNotFound extends RuntimeException {
    public NonExistentStateNotFound() {
        super("Given US state does not exist.");
    }
}
