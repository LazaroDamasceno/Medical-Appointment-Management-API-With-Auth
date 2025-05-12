package com.api.v1.people.exceptions;

public class DuplicatedSinException extends RuntimeException {
    public DuplicatedSinException(String message) {
        super("Provided SIN is currently in use.");
    }
}
