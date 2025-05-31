package com.api.v1.people;

public class DuplicatedSINException extends RuntimeException {
    public DuplicatedSINException() {
        super("Provided Social Identification Number is currently in use.");
    }
}
