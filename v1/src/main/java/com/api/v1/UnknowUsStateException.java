package com.api.v1;

public class UnknowUsStateException extends RuntimeException {
    public UnknowUsStateException(String stateCode) {
        super("The US State's code %s does not exist.".formatted(stateCode));
    }
}
