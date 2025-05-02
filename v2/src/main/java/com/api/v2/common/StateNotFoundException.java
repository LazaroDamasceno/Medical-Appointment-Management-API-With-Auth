package com.api.v2.common;

public class StateNotFoundException extends RuntimeException {
    public StateNotFoundException() {
        super("Given US state does not exist.");
    }
}
