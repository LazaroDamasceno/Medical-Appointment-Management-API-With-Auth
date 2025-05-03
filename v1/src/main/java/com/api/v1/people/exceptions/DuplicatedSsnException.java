package com.api.v1.people.exceptions;

public class DuplicatedSsnException extends RuntimeException {
    public DuplicatedSsnException() {
        super("The provided SSN is already in use.");
    }
}
