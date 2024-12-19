package com.api.v2.exceptions;

public class NonExistentSsnException extends RuntimeException {
    public NonExistentSsnException() {
        super("The given SSN is not registered in the system..");
    }
}
