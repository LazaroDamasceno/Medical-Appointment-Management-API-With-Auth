package com.api.v1.people.exceptions;

public class DuplicatedSINException extends RuntimeException {
    public DuplicatedSINException() {
        super("Provided SIN is currently in use.");
    }
}
