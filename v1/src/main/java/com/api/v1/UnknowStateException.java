package com.api.v1;

public class UnknowStateException extends RuntimeException {
    public UnknowStateException(String stateCode) {
        super("The State's code %s does not exist.".formatted(stateCode));
    }
}
