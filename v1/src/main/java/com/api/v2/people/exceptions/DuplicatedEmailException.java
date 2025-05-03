package com.api.v1.people.exceptions;

public class DuplicatedEmailException extends RuntimeException {
    public DuplicatedEmailException() {
        super("The provided email is already in use.");
    }
}
