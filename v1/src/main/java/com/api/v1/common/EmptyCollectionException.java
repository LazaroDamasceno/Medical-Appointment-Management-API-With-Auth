package com.api.v1.common;

public class EmptyCollectionException extends RuntimeException {
    public EmptyCollectionException() {
        super("Sought collection is empty.");
    }
}
