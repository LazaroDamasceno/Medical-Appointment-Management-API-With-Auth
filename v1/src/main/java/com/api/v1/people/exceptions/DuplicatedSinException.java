package com.api.v1.people.exceptions;

public class DuplicatedSinException extends RuntimeException {
    public DuplicatedSinException() {
        super("The provided SSN is already in use.");
    }
}
