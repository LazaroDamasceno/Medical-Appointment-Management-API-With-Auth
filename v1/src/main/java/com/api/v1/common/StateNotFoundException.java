package com.api.v1.common;

public class StateNotFoundException extends RuntimeException {
    public StateNotFoundException() {
        super("Given US state does not exist.");
    }
}
