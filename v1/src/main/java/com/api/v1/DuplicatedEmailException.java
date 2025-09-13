package com.api.v1;

public class DuplicatedEmailException extends RuntimeException {
    public DuplicatedEmailException() {
        super("The email address is already registered.");
    }
}
