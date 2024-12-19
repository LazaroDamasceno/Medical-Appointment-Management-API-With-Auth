package com.api.v2.exceptions;

public class DuplicatedSsnException extends RuntimeException {
    public DuplicatedSsnException() {
        super("The given SSN is already in use.");
    }
}
