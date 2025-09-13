package com.api.v1;

public class UnknowUsStateException extends RuntimeException {
    public UnknowUsStateException(String stateCode) {
        super(ErrorMessage.unknowUsState(stateCode));
    }
}
