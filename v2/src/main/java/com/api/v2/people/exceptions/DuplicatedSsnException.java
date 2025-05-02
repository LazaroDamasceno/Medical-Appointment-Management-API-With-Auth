package com.api.v2.people.exceptions;

public class DuplicatedSsnException extends RuntimeException {
    public DuplicatedSsnException() {
        super("The provided SSN is already in use.");
    }
}
