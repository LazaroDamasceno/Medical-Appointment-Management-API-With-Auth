package com.api.v2.people.exceptions;

public class NonExistentSsnException extends RuntimeException {
    public NonExistentSsnException() {
        super("The given SSN is not registered in the system..");
    }
}
