package com.api.v1.nurses.exceptions;

public class NurseNotFoundException extends RuntimeException {

    public NurseNotFoundException() {
        super("Sought nurse was not found.");
    }
}
