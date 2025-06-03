package com.api.v1.people.exceptions;

public class DuplicatedEmailException extends RuntimeException {
    public DuplicatedEmailException() {
        super("Provided email is currently in use.");
    }
}
