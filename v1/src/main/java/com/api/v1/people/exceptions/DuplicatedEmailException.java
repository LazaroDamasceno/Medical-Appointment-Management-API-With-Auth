package com.api.v1.people.exceptions;

public class DuplicatedEmailException extends RuntimeException {
    public DuplicatedEmailException() {
        super("The email address is already registered.");
    }
}
